package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyChatApi;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.request.ChatMessageRequest;
import io.github.chatpass.dify.data.response.ChatMessageResponse;
import io.github.chatpass.dify.util.JSONUtils;

import java.util.Map;

public class DifySendChatMessageExample {
    public static void main(String[] args) {
        Map<String,String> envs = System.getenv();
        DifyChatApi difyChatApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyChatApi();
        String userId = "test-user-" + System.currentTimeMillis();
        ChatMessageRequest request = new ChatMessageRequest();
        request.setUser(userId);
        request.setQuery("你好，请介绍一下自己");
        request.setResponseMode(ResponseMode.BLOCKING);
        ChatMessageResponse response = difyChatApi.sendChatMessage(request);
        System.out.println(JSONUtils.toJson(response));
    }
}
