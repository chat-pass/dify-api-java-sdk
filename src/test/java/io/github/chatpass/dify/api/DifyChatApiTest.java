package io.github.chatpass.dify.api;

import io.github.chatpass.dify.api.callback.ChatStreamCallback;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.request.ChatMessageRequest;
import io.github.chatpass.dify.data.request.StopChatMessageRequest;
import io.github.chatpass.dify.data.response.ChatMessageResponse;
import io.github.chatpass.dify.data.response.StopChatMessageResponse;
import io.github.chatpass.dify.data.response.SuggestedQuestionsResponse;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DifyChatApiTest extends MockServerUtilsTest{

    private static final String USER_ID = "test-user-" + System.currentTimeMillis();

    @Test
    public void testSendChatMessage() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"event\": \"message\",\n" +
                "  \"task_id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "  \"id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "  \"message_id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "  \"conversation_id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "  \"mode\": \"chat\",\n" +
                "  \"answer\": \"This is a mock answer.\",\n" +
                "  \"metadata\": {\n" +
                "    \"usage\": {\n" +
                "      \"prompt_tokens\": 150,\n" +
                "      \"prompt_unit_price\": \"0.01\",\n" +
                "      \"prompt_price_unit\": \"USD\",\n" +
                "      \"prompt_price\": \"1.50\",\n" +
                "      \"completion_tokens\": 200,\n" +
                "      \"completion_unit_price\": \"0.02\",\n" +
                "      \"completion_price_unit\": \"USD\",\n" +
                "      \"completion_price\": \"4.00\",\n" +
                "      \"total_tokens\": 350,\n" +
                "      \"total_price\": \"5.50\",\n" +
                "      \"currency\": \"USD\",\n" +
                "      \"latency\": 250\n" +
                "    },\n" +
                "    \"retriever_resources\": [\n" +
                "      {\n" +
                "        \"position\": 1,\n" +
                "        \"dataset_id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "        \"dataset_name\": \"Sample Dataset\",\n" +
                "        \"document_id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "        \"document_name\": \"Sample Document\",\n" +
                "        \"segment_id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "        \"score\": 95.5,\n" +
                "        \"content\": \"This is a sample content.\"\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"created_at\": 1672531199\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 创建请求对象
        ChatMessageRequest request = new ChatMessageRequest();
        request.setQuery("你好，请介绍一下自己");
        request.setUser(USER_ID);
        request.setResponseMode(ResponseMode.BLOCKING);
        // 调用方法
        ChatMessageResponse response = difyChatApi.sendChatMessage(request);

        // 验证响应
        assertEquals("This is a mock answer.", response.getAnswer());
    }


    @Test
    public void testSendChatMessageStream() {
        // 设置模拟流式响应
        MockResponse mockResponse = new MockResponse()
                .setBody("data: {\"event\":\"message\",\"data\":\"Hello, world!\"}\n\n")
                .addHeader("Content-Type", "text/event-stream");
        mockWebServer.enqueue(mockResponse);

        // 创建请求对象
        ChatMessageRequest request = new ChatMessageRequest();
        request.setResponseMode(ResponseMode.STREAMING);

        // 创建回调对象
        ChatStreamCallback callback = mock(ChatStreamCallback.class);

        // 调用方法
        difyChatApi.sendChatMessageStream(request, callback);

        // 验证回调
        verify(callback, never()).onException(any());
    }

    @Test
    public void testStopChatMessage() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"result\": \"success\"\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 创建请求对象
        StopChatMessageRequest request = new StopChatMessageRequest();
        // 设置请求的必要字段
        request.setUser("user-1");
        // 调用方法
        StopChatMessageResponse response = difyChatApi.stopChatMessage("12345", request);

        // 验证响应
        assertEquals("success", response.getResult());
    }

    @Test
    public void testGetSuggestedQuestions() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"result\": \"success\",\n" +
                "  \"data\": [\n" +
                "    \"What is your name?\",\n" +
                "\t\"How can I help you?\"\n" +
                "  ]\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 调用方法
        SuggestedQuestionsResponse response = difyChatApi.getSuggestedQuestions("123", "john_doe");

        // 验证响应
        assertEquals(2, response.getData().size());
        assertEquals("What is your name?", response.getData().get(0));
        assertEquals("How can I help you?", response.getData().get(1));
    }
}
