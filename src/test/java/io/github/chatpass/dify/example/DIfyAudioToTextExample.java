package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyChatApi;
import io.github.chatpass.dify.data.response.AudioToTextResponse;
import io.github.chatpass.dify.util.JSONUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DIfyAudioToTextExample {
    public static void main(String[] args) throws IOException {
        Map<String,String> envs = System.getenv();
        DifyChatApi difyChatApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyChatApi();
        String userId = "test-user-" + System.currentTimeMillis();
        AudioToTextResponse response = difyChatApi.audioToText(new File("output.wav"),userId);
        System.out.println(JSONUtils.toJson(response));
    }
}
