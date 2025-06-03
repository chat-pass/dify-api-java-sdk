package io.github.chatpass.dify;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.github.chatpass.dify.api.callback.StreamCallback;
import io.github.chatpass.dify.api.interceptor.AuthenticationInterceptor;
import io.github.chatpass.dify.api.proxy.CustomHttpProxySelector;
import io.github.chatpass.dify.api.proxy.CustomProxyAuthenticator;
import io.github.chatpass.dify.data.event.BaseEvent;
import io.github.chatpass.dify.exception.DifyApiError;
import io.github.chatpass.dify.exception.DifyApiException;
import io.github.chatpass.dify.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.*;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
public class DIfyApiServiceGenerator {

    private static volatile OkHttpClient sharedClient = createDefaultHttpClient();
    private static final String DONE_MARKER = "[DONE]";
    private static final String DATA_PREFIX = "data:";
    private static final Converter.Factory converterFactory = JacksonConverterFactory.create(
            new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                    .configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false)
                    .configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false)
                    .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
    );

    private static final Converter<ResponseBody, DifyApiError> errorBodyConverter = (Converter<ResponseBody, DifyApiError>) converterFactory.responseBodyConverter(DifyApiError.class, new Annotation[0], null);

    public static OkHttpClient createDefaultHttpClient() {
        return new OkHttpClient.Builder()//
                .callTimeout(120, TimeUnit.SECONDS)//
                .pingInterval(20, TimeUnit.SECONDS)//
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
    }

    public static void configureHttpClient(OkHttpClient client) {
        Objects.requireNonNull(client, "Client cannot be null");
        sharedClient = client;
    }

    /**
     * Sets http proxy for the shared client.
     * <p>If you need to use a proxy to connect to the internet,
     * you can use this method to set it.
     * <p>
     * <ul>
     * <li>If you want to use a proxy that requires authentication,
     * you can pass the username and password as parameters.
     * <p>
     * <li>If you want to use a proxy that does not require authentication,
     * you can pass {@code null} as parameters for {@code username} and {@code pwd}.
     * </ul>
     * <p>
     *
     * @param host     the host (Not null)
     * @param port     the port
     * @param username the username
     * @param pwd      the pwd
     * @see <a href="https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/-builder/proxy-selector/">Proxy Selector</a>
     * @see <a href="https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/-builder/proxy-authenticator/">Proxy Authenticator</a>
     */
    public static void setHttpProxy(String host, int port, String username, String pwd) {
        Objects.requireNonNull(host, "Host cannot be null");
        CustomHttpProxySelector proxySelector = new CustomHttpProxySelector(host, port);

        sharedClient = sharedClient.newBuilder()
                .proxySelector(proxySelector)
                .build();

        if (username == null || pwd == null) {
            //Without authentication
            return;
        }

        CustomProxyAuthenticator proxyAuthenticator = new CustomProxyAuthenticator(username, pwd);

        sharedClient = sharedClient.newBuilder()
                .proxyAuthenticator(proxyAuthenticator)
                .build();
    }

    public static <S> S createService(Class<S> serviceClass, String apiKey, String baseUrl) {
        if (!baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory);
        if (apiKey == null) {
            retrofitBuilder.client(sharedClient);
        } else {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(apiKey);
            OkHttpClient adaptedClient = sharedClient.newBuilder()
                    .addInterceptor(interceptor)
                    .build();
            retrofitBuilder.client(adaptedClient);
        }
        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(serviceClass);
    }

    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                DifyApiError apiError = getDifyApiError(response);
                throw new DifyApiException(apiError);
            }
        } catch (IOException e) {
            log.error("exceute sync error {}",e);
            throw new DifyApiException(e);
        }
    }

    public static void executeStreamRequest(Call<ResponseBody> call, LineProcessor lineProcessor, Consumer<Throwable> errorHandler) {
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                log.error("stream request error {}",e);
                errorHandler.accept(e);
            }

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    try {
                        String errorBody = response.body() != null ? response.body().string() : "";
                        DifyApiException exception = createDifyApiException(response.code(), errorBody);
                        log.error("stream request error {}",exception);
                        errorHandler.accept(exception);
                    } catch (IOException e) {
                        log.error("stream request error {}",e);
                        errorHandler.accept(e);
                    }
                    return;
                }

                try (ResponseBody responseBody = response.body()) {
                    if (responseBody == null) {
                        IOException exception = new IOException("response is null");
                        errorHandler.accept(exception);
                        return;
                    }

                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseBody.byteStream(), StandardCharsets.UTF_8))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.isEmpty()) {
                                continue;
                            }

                            // 处理行，如果返回false则停止处理
                            if (!lineProcessor.process(line)) {
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("process stream response error {}",e);
                    errorHandler.accept(e);
                }
            }
        });
    }

    private static DifyApiException createDifyApiException(int code, String message) {
        String errorCode = "unknown_error";
        String errorMessage = message;
        try {
            if (message != null && !message.isEmpty() && JSONUtils.isValidJson(message)) {
                DifyApiError error = JSONUtils.fromJson(message,DifyApiError.class);
                if(error != null){
                    return new DifyApiException(error);
                }
            }
        }catch (Exception e){

        }
        DifyApiError difyApiError = new DifyApiError(code,errorCode,errorMessage);
        return new DifyApiException(difyApiError);
    }

    public static boolean processStreamLine(String line, StreamCallback callback, EventProcessor eventProcessor) {
        if (line.startsWith(DATA_PREFIX)) {
            String data = line.substring(DATA_PREFIX.length()).trim();
            if (DONE_MARKER.equals(data)) {
                callback.onComplete();
                return false;
            }

            try {
                // 解析事件类型
                BaseEvent baseEvent = JSONUtils.fromJson(data, BaseEvent.class);
                if (baseEvent == null) {
                    return true;
                }

                // 处理事件
                eventProcessor.process(data, baseEvent.getEvent());
            } catch (Exception e) {
                callback.onException(e);
            }
        }
        return true; // 继续处理
    }

    @FunctionalInterface
    public interface LineProcessor {
        boolean process(String line);
    }

    @FunctionalInterface
    public interface EventProcessor {
        /**
         * 处理事件
         * @param data 事件数据
         * @param eventType 事件类型
         */
        void process(String data, String eventType);
    }
    public static DifyApiError getDifyApiError(Response<?> response) throws DifyApiException, IOException {
        Objects.requireNonNull(errorBodyConverter);
        ResponseBody responseBody = response.errorBody();
        Objects.requireNonNull(responseBody);
        if(responseBody.contentLength() <= 0){
            return new DifyApiError(response.code(),response.message());
        }
        return errorBodyConverter.convert(responseBody);
    }
}
