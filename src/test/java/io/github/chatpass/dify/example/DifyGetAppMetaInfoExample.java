package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyBaseApi;
import io.github.chatpass.dify.data.response.AppMetaInfoResponse;
import io.github.chatpass.dify.util.JSONUtils;

import java.util.Map;

public class DifyGetAppMetaInfoExample {
    public static void main(String[] args) {
        Map<String,String> envs = System.getenv();
        DifyBaseApi difyBaseApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyBaseApi();
        AppMetaInfoResponse appInfoResponse = difyBaseApi.getAppMetaInfo();
        System.out.println(JSONUtils.toJson(appInfoResponse));
    }
}
