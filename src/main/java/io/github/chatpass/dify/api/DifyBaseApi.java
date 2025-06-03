package io.github.chatpass.dify.api;

import io.github.chatpass.dify.data.request.UploadFileRequest;
import io.github.chatpass.dify.data.response.*;
import io.github.chatpass.dify.data.response.*;
import io.github.chatpass.dify.service.DifyBaseApiService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

import static io.github.chatpass.dify.DIfyApiServiceGenerator.createService;
import static io.github.chatpass.dify.DIfyApiServiceGenerator.executeSync;

@Slf4j
public class DifyBaseApi {

    private final String baseUrl;
    private final String apiKey;
    private final DifyBaseApiService difyBaseApiService;

    public DifyBaseApi(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.difyBaseApiService = createService(DifyBaseApiService.class,apiKey,baseUrl);
    }

    public AppInfoResponse getAppInfo(){
        return executeSync(difyBaseApiService.getAppInfo());
    }

    public AppParametersResponse getAppParameters(){
        return executeSync(difyBaseApiService.getAppParameters());
    }

    public AppMetaInfoResponse getAppMetaInfo(){
        return executeSync(difyBaseApiService.getAppMetaInfo());
    }

    public WebAppResponse getWebAppInfo(){
        return executeSync(difyBaseApiService.getWebAppInfo());
    }

    public UploadFileResponse uploadFile(UploadFileRequest request, File file){
        RequestBody requestFile = RequestBody.create(file,MediaType.parse("multipart/form-data"));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        MultipartBody.Part userPart = MultipartBody.Part.createFormData("user", request.getUser());
        return executeSync(difyBaseApiService.uploadFile(filePart,userPart));
    }
}
