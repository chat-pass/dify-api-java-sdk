package io.github.chatpass.dify.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.chatpass.dify.api.callback.ChatflowStreamCallback;
import io.github.chatpass.dify.data.event.*;
import io.github.chatpass.dify.util.JSONUtils;
import io.github.chatpass.dify.data.enums.AnnotationReplyAction;
import io.github.chatpass.dify.data.enums.RatingMode;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.request.AnnotationReplyRequest;
import io.github.chatpass.dify.data.request.AnnotationRequest;
import io.github.chatpass.dify.data.request.ChatMessageRequest;
import io.github.chatpass.dify.data.request.StopChatMessageRequest;
import org.junit.jupiter.api.Test;

import io.github.chatpass.dify.data.request.SimpleUserRequest;
import io.github.chatpass.dify.data.request.FeedBackRequest;
import io.github.chatpass.dify.data.request.RenameConversationRequest;
import io.github.chatpass.dify.data.request.TextToAudioRequest;
import io.github.chatpass.dify.data.response.ChatMessageResponse;
import io.github.chatpass.dify.data.response.StopChatMessageResponse;
import io.github.chatpass.dify.data.response.SuggestedQuestionsResponse;
import io.github.chatpass.dify.data.response.ConversationListResponse;
import io.github.chatpass.dify.data.response.ChatMessageListResponse;
import io.github.chatpass.dify.data.response.LittleResponse;
import io.github.chatpass.dify.data.response.ConversationVariablesResponse;
import io.github.chatpass.dify.data.response.FeedBackListResponse;
import io.github.chatpass.dify.data.response.Conversation;
import io.github.chatpass.dify.data.response.AudioToTextResponse;
import io.github.chatpass.dify.data.response.Annotation;
import io.github.chatpass.dify.data.response.AnnotationListResponse;
import io.github.chatpass.dify.data.response.AnnotationReplyResponse;

import java.io.IOException;
import java.util.HashMap;
import okhttp3.mockwebserver.MockResponse;
import java.io.File;


class DifyChatFlowApiTest extends MockServerUtilsTest{


    private String conversationId = "7c38ee44-5518-4833-8a21-eee8e579fd18";

    private String messageId = "8e0a96af-783d-46e2-887f-9dd9ab4a6453";
    private String user = "abc-123";

    @Test
    void sendChatMessage() throws IOException {
        // Arrange
        String mockResponseBody = "{\n" +
                "  \"event\": \"message\",\n" +
                "  \"message_id\": \"123\",\n" +
                "  \"conversation_id\": \"" + conversationId + "\",\n" +
                "  \"mode\": \"chatflow\",\n" +
                "  \"answer\": \"This is a mock answer.\",\n" +
                "  \"created_at\": 1672531199\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        ChatMessageRequest request = new ChatMessageRequest(); // Populate with test data
        request.setUser(user);
        request.setQuery("Hello");
        request.setInputs(new HashMap<>());
        request.setResponseMode(ResponseMode.BLOCKING);
        request.setConversationId(conversationId);
        ChatMessageResponse actualResponse = difyChatFlowApi.sendChatMessage(request);
        System.out.println(JSONUtils.toJson(actualResponse));
        assertNotNull(actualResponse.getAnswer());    
    }

    @Test
    void sendChatMessageStream_shouldSetStreamingModeAndInvokeService() throws Exception {
        // Arrange

        // 设置模拟流式响应
        MockResponse mockResponse = new MockResponse()
                .setBody("data: {\"event\":\"message\",\"data\":\"Hello, world!\"}\n\n")
                .addHeader("Content-Type", "text/event-stream");
        mockWebServer.enqueue(mockResponse);

        // 创建请求对象
        ChatMessageRequest request = new ChatMessageRequest();
        request.setUser(user);
        request.setQuery("Hello stream");
        request.setInputs(new HashMap<>());
        request.setResponseMode(ResponseMode.STREAMING);

        // 创建回调对象
        ChatflowStreamCallback callback = mock(ChatflowStreamCallback.class);

        // 调用方法
        difyChatFlowApi.sendChatMessageStream(request, callback);

        // 验证回调
        verify(callback, never()).onException(any());

    }

    @Test
    void stopChatMessage() throws IOException {
        // Arrange
        String mockResponseBody = "{\n" +
                "  \"result\": \"success\"\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));
        String taskId = "task-123";
        StopChatMessageRequest request = new StopChatMessageRequest();
        request.setUser(user);

        // Act
        StopChatMessageResponse actualResponse = difyChatFlowApi.stopChatMessage(taskId, request);
        System.out.println(JSONUtils.toJson(actualResponse));
        // Assert
        assertNotNull(actualResponse);
        assertEquals("success", actualResponse.getResult());
    }

    @Test
    void getSuggestedQuestions() throws IOException {
        // Arrange
        String mockResponseBody = "{\n" +
                "  \"result\": \"success\",\n" +
                "  \"data\": [\"What is your name?\", \"How can I help you?\"]\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        String messageId = "d9ee20f6-91a0-4c2c-8688-5abc960d8155";

        // Act
        SuggestedQuestionsResponse actualResponse = difyChatFlowApi.getSuggestedQuestions(messageId, user);
        System.out.println(JSONUtils.toJson(actualResponse));
        // Assert
        assertNotNull(actualResponse.getData());
    }

    @Test
    void getConversations_shouldReturnExpectedResponse_whenServiceCallSucceeds() throws IOException {
        // Arrange
        String mockResponseBody = "{\n" +
                "  \"limit\": 10,\n" +
                "  \"has_more\": false,\n" +
                "  \"data\": []\n" + // Simplified for brevity
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));
        Integer limit = 10;
        String sortBy = "created_at";

        // Act
        ConversationListResponse actualResponse = difyChatFlowApi.getConversations(user, null, limit, sortBy);
        System.out.println(actualResponse);

        // Assert
        assertNotNull(actualResponse);;
    }

    @Test
    void getMessages_shouldReturnExpectedResponse_whenServiceCallSucceeds() throws IOException {
        // Arrange
        String mockResponseBody = "{\n" +
                "  \"limit\": 20,\n" +
                "  \"has_more\": false,\n" +
                "  \"data\": []\n" + // Simplified for brevity
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));
        Integer limit = 20;
        String firstId = messageId;
        // Act
        ChatMessageListResponse actualResponse = difyChatFlowApi.getMessages(conversationId, user, firstId, limit);
        System.out.println(actualResponse);
        // Assert
        assertNotNull(actualResponse);
    }



    @Test
    void getConversationVariables_shouldReturnExpectedResponse_whenServiceCallSucceeds() throws IOException {
        // Arrange
        String mockResponseBody = "{\n" +
                "  \"data\": [],\n" + // Simplified for brevity
                "  \"total\": 0,\n" +
                "  \"limit\": 5,\n" +
                "  \"page\": 1,\n" +
                "  \"has_more\": false\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));
        Integer limit = 5;
               // Act
        ConversationVariablesResponse actualResponse = difyChatFlowApi.getConversationVariables(conversationId, user, null, limit, null);
        System.out.println(JSONUtils.toJson(actualResponse));
        assertNotNull(actualResponse);
    }

    @Test
    void feedbackMessage_shouldReturnExpectedResponse_whenServiceCallSucceeds() throws IOException {
        // Arrange
        String mockResponseBody = "{\n" +
                "  \"result\": \"success\"\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));
        FeedBackRequest request = new FeedBackRequest();
        request.setUser(user);
        request.setRating(RatingMode.LIKE);

        // Act
        LittleResponse actualResponse = difyChatFlowApi.feedbackMessage(messageId, request);
        System.out.println(JSONUtils.toJson(actualResponse));
        // Assert
        assertNotNull(actualResponse);
        assertEquals("success", actualResponse.getResult());
    }

    @Test
    void getFeedBackList_shouldReturnExpectedResponse_whenServiceCallSucceeds() throws IOException {
        // Arrange
        String mockResponseBody = "{\n" +
                "  \"data\": [],\n" + // Simplified for brevity
                "  \"total\": 0,\n" +
                "  \"limit\": 10,\n" +
                "  \"page\": 1,\n" +
                "  \"has_more\": false\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));
        Integer page = 1;
        Integer limit = 10;
        // Act
        FeedBackListResponse actualResponse = difyChatFlowApi.getFeedBackList(page, limit);
        System.out.println(JSONUtils.toJson(actualResponse));
        // Assert
        assertNotNull(actualResponse);
    }

    @Test
    void deleteConversation_shouldReturnExpectedResponse_whenServiceCallSucceeds() throws IOException {
        // Arrange
        String mockResponseBody = "{\n" +
                "  \"result\": \"success\"\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));
        SimpleUserRequest userRequest = new SimpleUserRequest(user);

        // Act
        LittleResponse actualResponse = difyChatFlowApi.deleteConversation(conversationId, userRequest);
        System.out.println(JSONUtils.toJson(actualResponse));
        // Assert
        assertNotNull(actualResponse);
        assertEquals("success", actualResponse.getResult());
    }

    @Test
    void renameConversation_shouldReturnRenamedConversation_whenServiceCallSucceeds() throws IOException {
        // Arrange
        String newName = "测试名称2";
        String mockResponseBody = String.format("{\n" +
                "  \"id\": \"%s\",\n" +
                "  \"name\": \"%s\",\n" +
                "  \"inputs\": {},\n" +
                "  \"status\": \" ενεργός\",\n" +
                "  \"created_at\": 1672531199\n" +
                "}", conversationId, newName);

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        RenameConversationRequest request = new RenameConversationRequest();
        request.setName(newName);
        request.setUser(user);

        // Act
        Conversation actualResponse = difyChatFlowApi.renameConversation(conversationId, request);
        System.out.println(JSONUtils.toJson(actualResponse));

        // Assert
        assertNotNull(actualResponse);
        assertEquals(conversationId, actualResponse.getId());
        assertEquals(newName, actualResponse.getName());
    }

    @Test
    void textToAudio_shouldReturnAudioBytes_whenServiceCallSucceeds() throws IOException {
        // Arrange
        String mockText = "Hello world";
        byte[] expectedAudioBytes = "mock audio data".getBytes();

        mockWebServer.enqueue(new MockResponse()
                .setBody(new okio.Buffer().write(expectedAudioBytes)) // MockWebServer expects Buffer for binary response
                .addHeader("Content-Type", "audio/mpeg"));

        TextToAudioRequest request = new TextToAudioRequest();
        request.setText(mockText);

        // Act
        byte[] actualAudioBytes = difyChatFlowApi.textToAudio(request);

        // Assert
        assertNotNull(actualAudioBytes);
    }

    @Test
    void audioToText_shouldReturnText_whenServiceCallSucceeds() throws IOException {
        String expectedText = "This is a mock transcription.";
        String mockFileName = "testaudio.mp3";
        String mockMimeType = "audio/mpeg";

        String mockResponseBody = String.format("{\n" +
                "  \"text\": \"%s\"\n" +
                "}", expectedText);

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        File mockFile = new File(mockFileName); // This will be a file in the root of the project
        // Create a dummy file for testing
        if (!mockFile.exists()) {
            mockFile.createNewFile();
        }
        AudioToTextResponse actualResponse = difyChatFlowApi.audioToText(mockFile, user);
        System.out.println(JSONUtils.toJson(actualResponse));

        // Assert
        assertNotNull(actualResponse);
        assertEquals(expectedText, actualResponse.getText());

        if (mockFile.exists()) {
            mockFile.delete();
        }
    }

    @Test
    void createAnnotation_shouldReturnAnnotation_whenServiceCallSucceeds() throws IOException {
        // Arrange
        String mockAnnotationId = "anno-123";
        String mockQuestion = "What is Dify?";

        String mockResponseBody = String.format("{\n" +
                "  \"id\": \"%s\",\n" +
                "  \"question\": \"%s\",\n" +
                "  \"answer\": \"Dify is an LLM application development platform.\",\n" +
                "  \"created_at\": 1672531199\n" +
                "}", mockAnnotationId, mockQuestion);

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        AnnotationRequest request = new AnnotationRequest();
        request.setQuestion(mockQuestion);
        request.setAnswer("Dify is an LLM application development platform.");
        // Populate other fields of AnnotationRequest as needed

        // Act
        Annotation actualResponse = difyChatFlowApi.createAnnotation(request);
        System.out.println(JSONUtils.toJson(actualResponse));

        // Assert
        assertNotNull(actualResponse);
        assertEquals(mockAnnotationId, actualResponse.getId());
        assertEquals(mockQuestion, actualResponse.getQuestion());
    }

    @Test
    void getAnnotationList_shouldReturnListOfAnnotations_whenServiceCallSucceeds() throws IOException {
        // Arrange
        int page = 1;
        int limit = 10;
        String mockResponseBody = "{\n" +
                "  \"limit\": 10,\n" +
                "  \"total\": 1,\n" +
                "  \"page\": 1,\n" +
                "  \"has_more\": false,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": \"anno-123\",\n" +
                "      \"question\": \"What is Dify?\",\n" +
                "      \"answer\": \"Dify is an LLM application development platform.\",\n" +
                "      \"created_at\": 1672531199\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // Act
        AnnotationListResponse actualResponse = difyChatFlowApi.getAnnotationList(page, limit);
        System.out.println(JSONUtils.toJson(actualResponse));

        // Assert
        assertNotNull(actualResponse);
    }

    @Test
    void deleteAnnotation_shouldCompleteSuccessfully_whenServiceCallSucceeds() throws IOException {
        String annotationId = "anno-to-delete-123";
        String mockResponseBody = "{\"result\": \"success\"}"; 

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        assertDoesNotThrow(() -> difyChatFlowApi.deleteAnnotation(annotationId));
    }

    @Test
    void updateAnnotation_shouldReturnUpdatedAnnotation_whenServiceCallSucceeds() throws IOException {
        // Arrange
        String annotationId = "anno-to-update-123";
        String updatedQuestion = "What is new with Dify?";
        String updatedAnswer = "Dify now supports more models.";

        String mockResponseBody = String.format("{\n" +
                "  \"id\": \"%s\",\n" +
                "  \"question\": \"%s\",\n" +
                "  \"answer\": \"%s\",\n" +
                "  \"created_at\": 1672531199\n" +
                "}", annotationId, updatedQuestion, updatedAnswer);

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        AnnotationRequest request = new AnnotationRequest();
        request.setQuestion(updatedQuestion);
        request.setAnswer(updatedAnswer);
        // Populate other fields of AnnotationRequest if they can be updated

        // Act
        Annotation actualResponse = difyChatFlowApi.updateAnnotation(annotationId, request);
        System.out.println(JSONUtils.toJson(actualResponse));

        // Assert
        assertNotNull(actualResponse);
        assertEquals(annotationId, actualResponse.getId());
        assertEquals(updatedQuestion, actualResponse.getQuestion());
        assertEquals(updatedAnswer, actualResponse.getAnswer());
    }

    @Test
    void annotationReply_shouldReturnReplyResponse_whenServiceCallSucceeds() throws IOException {
        String mockJobId = "job-123";
        String mockStatus = "succeeded";

        String mockResponseBody = String.format("{\n" +
                "  \"job_id\": \"%s\",\n" +
                "  \"job_status\": \"%s\"\n" +
                "}", mockJobId, mockStatus);

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        AnnotationReplyRequest request = new AnnotationReplyRequest();
        request.setEmbeddingProviderName("zhipu");
        request.setEmbeddingModelName("embedding_3");
        request.setScoreThreshold(0.9);

        // Act
        AnnotationReplyResponse actualResponse = difyChatFlowApi.annotationReply(AnnotationReplyAction.ENABLE, request);
        System.out.println(JSONUtils.toJson(actualResponse));

        // Assert
        assertNotNull(actualResponse);
    }

    @Test
    void getAnnotationReplyStatus_shouldReturnReplyStatusResponse_whenServiceCallSucceeds() throws IOException {
        // Arrange
        String mockJobId = "job-123";
        String mockStatus = "processing"; // Example status

        String mockResponseBody = String.format("{\n" +
                "  \"job_id\": \"%s\",\n" +
                "  \"job_status\": \"%s\"\n" +
                "}", mockJobId, mockStatus);

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // Act
        AnnotationReplyResponse actualResponse = difyChatFlowApi.getAnnotationReplyStatus(AnnotationReplyAction.ENABLE, mockJobId);
        System.out.println(JSONUtils.toJson(actualResponse));

        // Assert
        assertNotNull(actualResponse);
    }
} 