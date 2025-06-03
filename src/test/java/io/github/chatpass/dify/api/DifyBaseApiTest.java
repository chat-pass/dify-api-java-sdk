package io.github.chatpass.dify.api;

import io.github.chatpass.dify.data.request.UploadFileRequest;
import io.github.chatpass.dify.data.response.AppInfoResponse;
import io.github.chatpass.dify.data.response.AppParametersResponse;
import io.github.chatpass.dify.data.response.UploadFileResponse;
import io.github.chatpass.dify.data.response.WebAppResponse;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DifyBaseApiTest extends MockServerUtilsTest{

    @Test
    public void testGetAppInfo() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"name\": \"TestApp\",\n" +
                "  \"description\": \"testapp\",\n" +
                "  \"tags\": [\n" +
                "    \"chat\"\n" +
                "  ]\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));
        // 调用方法
        AppInfoResponse response = difyBaseApi.getAppInfo();

        // 验证响应
        assertEquals("TestApp", response.getName());
        assertEquals("testapp", response.getDescription());
        assertEquals(1, response.getTags().size());
    }

    @Test
    public void testGetAppParameters() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"opening_statement\": \"Welcome to our service!\",\n" +
                "  \"suggested_questions\": [\n" +
                "    \"How can I help you today?\"\n" +
                "  ],\n" +
                "  \"suggested_questions_after_answer\": {\n" +
                "    \"enabled\": true\n" +
                "  },\n" +
                "  \"speech_to_text\": {\n" +
                "    \"enabled\": true\n" +
                "  },\n" +
                "  \"text_to_speech\": {\n" +
                "    \"enabled\": true,\n" +
                "    \"voice\": \"en-US-Wavenet-D\",\n" +
                "    \"language\": \"en-US\",\n" +
                "    \"autoPlay\": \"enabled\"\n" +
                "  },\n" +
                "  \"retriever_resource\": {\n" +
                "    \"enabled\": true\n" +
                "  },\n" +
                "  \"annotation_reply\": {\n" +
                "    \"enabled\": true\n" +
                "  },\n" +
                "  \"user_input_form\": [\n" +
                "    {\n" +
                "      \"text-input\": {\n" +
                "        \"label\": \"Please enter your name\",\n" +
                "        \"variable\": \"userName\",\n" +
                "        \"required\": true,\n" +
                "        \"default\": \"John Doe\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"file_upload\": {\n" +
                "    \"image\": {\n" +
                "      \"enabled\": true,\n" +
                "      \"number_limits\": 5,\n" +
                "      \"transfer_methods\": [\n" +
                "        \"remote_url\"\n" +
                "      ]\n" +
                "    }\n" +
                "  },\n" +
                "  \"system_parameters\": {\n" +
                "    \"file_size_limit\": 10485760,\n" +
                "    \"image_file_size_limit\": 5242880,\n" +
                "    \"audio_file_size_limit\": 10485760,\n" +
                "    \"video_file_size_limit\": 20971520\n" +
                "  }\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        AppParametersResponse response = difyBaseApi.getAppParameters();

        assertEquals("Welcome to our service!", response.getOpeningStatement());
    }

    @Test
    public void testGetWebAppInfo() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"title\": \"Chat Application\",\n" +
                "  \"chat_color_theme\": \"#FF5733\",\n" +
                "  \"chat_color_theme_inverted\": false,\n" +
                "  \"icon_type\": \"emoji\",\n" +
                "  \"icon\": \"\uD83D\uDE0A\",\n" +
                "  \"icon_background\": \"#FFFFFF\",\n" +
                "  \"icon_url\": \"https://example.com/icon.png\",\n" +
                "  \"description\": \"This is a chat application for seamless communication.\",\n" +
                "  \"copyright\": \"© 2023 ChatApp Inc.\",\n" +
                "  \"privacy_policy\": \"https://example.com/privacy-policy\",\n" +
                "  \"custom_disclaimer\": \"This is a custom disclaimer for the chat application.\",\n" +
                "  \"default_language\": \"en\",\n" +
                "  \"show_workflow_steps\": true,\n" +
                "  \"use_icon_as_answer_icon\": true\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 调用方法
        WebAppResponse response = difyBaseApi.getWebAppInfo();

        assertEquals("Chat Application", response.getTitle());
        assertEquals(true, response.isUseIconAsAnswerIcon());
    }

    @Test
    public void testUploadFile() throws IOException {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "  \"name\": \"test\",\n" +
                "  \"size\": 1024,\n" +
                "  \"extension\": \"txt\",\n" +
                "  \"mime_type\": \"text/plain\",\n" +
                "  \"created_by\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "  \"created_at\": 1672531199\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 创建请求对象
        UploadFileRequest request = new UploadFileRequest();
        request.setUser("testUser");

        // 创建临时文件
        File tempFile = File.createTempFile("test", ".txt");
        tempFile.deleteOnExit();

        UploadFileResponse response = difyBaseApi.uploadFile(request, tempFile);

        assertEquals("test", response.getName());
    }
}
