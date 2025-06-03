package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyBaseApi;
import io.github.chatpass.dify.data.response.WebAppResponse;
import io.github.chatpass.dify.util.JSONUtils;

import java.util.Map;

public class DIfyGetWebAppInfo {
    public static void main(String[] args) {
        Map<String,String> envs = System.getenv();
        DifyBaseApi difyBaseApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyBaseApi();
        WebAppResponse webAppInfo = difyBaseApi.getWebAppInfo();
        System.out.println(JSONUtils.toJson(webAppInfo));
    }
}
