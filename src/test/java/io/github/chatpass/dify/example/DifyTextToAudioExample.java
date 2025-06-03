package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyChatApi;
import io.github.chatpass.dify.data.request.TextToAudioRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class DifyTextToAudioExample {
    public static void main(String[] args) throws IOException {
        Map<String,String> envs = System.getenv();
        DifyChatApi difyChatApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyChatApi();
        String userId = "test-user-" + System.currentTimeMillis();
        TextToAudioRequest request = new TextToAudioRequest();
        request.setText("今天是一个好天气");
        request.setUser(userId);
        byte[] result = difyChatApi.textToAudio(request);
        if(result != null){
            Files.write(Paths.get("output.wav"), result);
        }
    }
}
