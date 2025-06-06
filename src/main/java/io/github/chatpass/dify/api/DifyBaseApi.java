package io.github.chatpass.dify.api;

import io.github.chatpass.dify.common.DifyConstants;
import io.github.chatpass.dify.data.request.UploadFileRequest;
import io.github.chatpass.dify.data.response.*;
import io.github.chatpass.dify.service.DifyBaseApiService;
import io.github.chatpass.dify.util.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

import static io.github.chatpass.dify.DIfyApiServiceGenerator.createService;
import static io.github.chatpass.dify.DIfyApiServiceGenerator.executeSync;

/**
 * Dify基础API客户端
 *
 * @author chat-pass
 */
@Slf4j
public class DifyBaseApi extends BaseApi {

    private final DifyBaseApiService difyBaseApiService;

    public DifyBaseApi(String baseUrl, String apiKey) {
        super(baseUrl, apiKey);
        this.difyBaseApiService = createService(DifyBaseApiService.class);
    }

    public AppInfoResponse getAppInfo() {
        return executeSync(difyBaseApiService.getAppInfo());
    }

    public AppParametersResponse getAppParameters() {
        return executeSync(difyBaseApiService.getAppParameters());
    }

    public AppMetaInfoResponse getAppMetaInfo() {
        return executeSync(difyBaseApiService.getAppMetaInfo());
    }

    public WebAppResponse getWebAppInfo() {
        return executeSync(difyBaseApiService.getWebAppInfo());
    }

    public UploadFileResponse uploadFile(UploadFileRequest request, File file) {
        ValidationUtils.validateNonNull(request, "request");
        ValidationUtils.validateFile(file, "file");
        ValidationUtils.validateNonBlank(request.getUser(), "user");

        RequestBody requestFile = RequestBody.create(file, MediaType.parse(DifyConstants.MULTIPART_FORM_DATA));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        MultipartBody.Part userPart = MultipartBody.Part.createFormData("user", request.getUser());
        return executeSync(difyBaseApiService.uploadFile(filePart, userPart));
    }
}
