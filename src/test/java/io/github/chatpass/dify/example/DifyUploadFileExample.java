package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyBaseApi;
import io.github.chatpass.dify.data.request.UploadFileRequest;
import io.github.chatpass.dify.data.response.UploadFileResponse;
import io.github.chatpass.dify.util.JSONUtils;

import java.io.File;
import java.util.Map;

public class DifyUploadFileExample {
    public static void main(String[] args) {
        Map<String,String> envs = System.getenv();
        DifyBaseApi difyBaseApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyBaseApi();
        String userId = "test-user-" + System.currentTimeMillis();
        UploadFileRequest request = new UploadFileRequest(userId);
        File file = new File("D:\\test.txt");
        UploadFileResponse response = difyBaseApi.uploadFile(request,file);
        System.out.println(JSONUtils.toJson(response));
    }
}
