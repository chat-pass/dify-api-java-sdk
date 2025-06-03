package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyChatApi;
import io.github.chatpass.dify.api.callback.ChatStreamCallback;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.event.*;
import io.github.chatpass.dify.data.request.ChatMessageRequest;
import io.github.chatpass.dify.data.event.*;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class DifySendChatStreamMessageExample {

    public static void main(String[] args) throws InterruptedException {
        Map<String,String> envs = System.getenv();
        DifyChatApi difyChatApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyChatApi();
        String userId = "test-user-" + System.currentTimeMillis();
        ChatMessageRequest request = new ChatMessageRequest();
        request.setUser(userId);
        request.setQuery("你好，请介绍一下自己");
        request.setResponseMode(ResponseMode.STREAMING);
        CountDownLatch latch = new CountDownLatch(1);
        StringBuilder responseBuilder = new StringBuilder();
        AtomicReference<String> messageId = new AtomicReference<>();
        difyChatApi.sendChatMessageStream(request, new ChatStreamCallback() {
            @Override
            public void onMessage(MessageEvent event) {
                System.out.println("receive chunk: " + event.getAnswer());
                responseBuilder.append(event.getAnswer());
            }

            @Override
            public void onMessageEnd(MessageEndEvent event) {
                System.out.println("message end messageID: " + event.getId());
                messageId.set(event.getId());
                latch.countDown();
            }

            @Override
            public void onMessageFile(MessageFileEvent event) {
                System.out.println("receive file: " + event);
            }

            @Override
            public void onTTSMessage(TtsMessageEvent event) {
                System.out.println("receive tts: " + event);
            }

            @Override
            public void onTTSMessageEnd(TtsMessageEndEvent event) {
                System.out.println("TTS end: " + event);
            }

            @Override
            public void onMessageReplace(MessageReplaceEvent event) {
                System.out.println("message replace: " + event);
            }

            @Override
            public void onAgentMessage(AgentMessageEvent event) {
                System.out.println("Agent message: " + event);
            }

            @Override
            public void onAgentThought(AgentThoughtEvent event) {
                System.out.println("Agent thought: " + event);
            }

            @Override
            public void onError(ErrorEvent event) {
                System.err.println("error: " + event.getMessage());
                latch.countDown();
            }

            @Override
            public void onException(Throwable throwable) {
                System.err.println("异常: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onPing(PingEvent event) {
                System.out.println("心跳: " + event);
            }
        });

        boolean completed = latch.await(100, TimeUnit.SECONDS);
        System.out.println(responseBuilder.toString());
    }
}
