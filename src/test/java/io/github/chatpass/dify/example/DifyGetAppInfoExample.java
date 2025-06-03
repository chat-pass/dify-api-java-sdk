package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyBaseApi;
import io.github.chatpass.dify.data.response.AppMetaInfoResponse;

import java.util.Map;

public class DifyGetAppInfoExample {
    public static void main(String[] args) {
        Map<String,String> envs = System.getenv();
        DifyBaseApi difyBaseApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyBaseApi();
        AppMetaInfoResponse appMetaInfoResponse = difyBaseApi.getAppMetaInfo();
        System.out.println(appMetaInfoResponse);
    }
}
