package io.github.chatpass.dify.service;

import io.github.chatpass.dify.data.response.*;
import io.github.chatpass.dify.data.response.*;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface DifyBaseApiService {
    /**
     * 获取应用的基本信息
     * @return
     */
    @GET("info")
    Call<AppInfoResponse> getAppInfo();

    @GET("parameters")
    Call<AppParametersResponse> getAppParameters();

    @GET("meta")
    Call<AppMetaInfoResponse> getAppMetaInfo();

    @GET("site")
    Call<WebAppResponse> getWebAppInfo();

    @Multipart
    @POST("files/upload")
    Call<UploadFileResponse> uploadFile(@Part MultipartBody.Part file, @Part MultipartBody.Part user);
}
