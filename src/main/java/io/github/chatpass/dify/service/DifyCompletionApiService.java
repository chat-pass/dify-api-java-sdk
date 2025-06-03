package io.github.chatpass.dify.service;

import io.github.chatpass.dify.data.request.CompletionMessageRequest;
import io.github.chatpass.dify.data.request.StopCompletionMessageRequest;
import io.github.chatpass.dify.data.response.ChatCompletionResponse;
import io.github.chatpass.dify.data.response.StopCompletionMessageResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

/**
 * 文本生成
 */
public interface DifyCompletionApiService extends DifyFeedBackApiService,DifySpeechToTextConversionApiService,DifyAnnotationApiService{

    @POST("completion-messages")
    Call<ChatCompletionResponse> sendCompletionMessage(@Body CompletionMessageRequest message);

    @POST("completion-messages")
    @Streaming
    Call<ResponseBody> sendCompletionMessageStream(@Body CompletionMessageRequest message);

    @POST("completion-messages/{task_id}/stop")
    Call<StopCompletionMessageResponse> stopCompletionMessage(@Path("task_id") String taskId, @Body StopCompletionMessageRequest request);
}
