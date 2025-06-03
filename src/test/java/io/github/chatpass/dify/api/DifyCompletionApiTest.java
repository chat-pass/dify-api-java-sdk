package io.github.chatpass.dify.api;

import io.github.chatpass.dify.api.callback.CompletionStreamCallback;
import io.github.chatpass.dify.data.enums.RatingMode;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.request.AnnotationRequest;
import io.github.chatpass.dify.data.request.CompletionMessageRequest;
import io.github.chatpass.dify.data.request.FeedBackRequest;
import io.github.chatpass.dify.data.request.StopCompletionMessageRequest;
import io.github.chatpass.dify.data.response.Annotation;
import io.github.chatpass.dify.data.response.AnnotationListResponse;
import io.github.chatpass.dify.data.response.ChatCompletionResponse;
import io.github.chatpass.dify.data.response.FeedBackListResponse;
import io.github.chatpass.dify.data.response.LittleResponse;
import io.github.chatpass.dify.data.response.StopCompletionMessageResponse;

import io.github.chatpass.dify.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;

import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
public class DifyCompletionApiTest extends MockServerUtilsTest{

    String user = "completion-user";

    String messageId = "74989d7c-caf1-4440-83f3-1865d3891955";


    @Test
    @SuppressWarnings("unchecked")
    void sendCompletionMessage_shouldReturnResponse_whenServiceCallSucceeds() throws IOException {
        // Define mock response
        String mockResponseBody = "{\n" +
                "  \"event\": \"message\",\n" +
                "  \"task_id\": \"task-123\",\n" +
                "  \"id\": \"msg-123\",\n" +
                "  \"message_id\": \"msg-123\",\n" +
                "  \"conversation_id\": \"conv-123\",\n" +
                "  \"mode\": \"completion\",\n" +
                "  \"answer\": \"This is a mock completion answer.\",\n" +
                "  \"metadata\": {\"usage\": {}},\n" +
                "  \"created_at\": 1672531199\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        Map<String, Object> inputs = new HashMap<>();
        inputs.put("A", "MySQL");
        inputs.put("default_input","Hello");
        CompletionMessageRequest request = CompletionMessageRequest.builder()
                .user(user)
                .inputs(inputs)
                .responseMode(ResponseMode.BLOCKING)
                .build();
        ChatCompletionResponse response = difyCompletionApi.sendCompletionMessage(request);
        System.out.println(JSONUtils.toJson(response));
        assertNotNull(response.getAnswer());
    }

    @Test
    void sendCompletionMessageStream_shouldReturnResponse_whenServiceCallSucceeds() throws Exception {
        // Setup mock stream response
        String mockStreamEvent1 = "data: {\"event\":\"message\",\"id\":\"msg-1\",\"task_id\":\"task-stream-123\",\"answer\":\"Hello\"}\n\n";
        String mockStreamEvent2 = "data: {\"event\":\"message\",\"id\":\"msg-2\",\"task_id\":\"task-stream-123\",\"answer\":\" World\"}\n\n";
        String mockStreamEndEvent = "data: {\"event\":\"message_end\",\"id\":\"msg-end\",\"task_id\":\"task-stream-123\", \"conversation_id\":\"conv-stream-123\"}\n\n";

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockStreamEvent1 + mockStreamEvent2 + mockStreamEndEvent)
                .addHeader("Content-Type", "text/event-stream"));

        Map<String, Object> inputs = new HashMap<>();
        inputs.put("A", "MySQL");
        inputs.put("default_input","Hello");
        CompletionMessageRequest request = CompletionMessageRequest.builder()
                .user(user)
                .inputs(inputs)
                .responseMode(ResponseMode.STREAMING)
                .build();

        CompletionStreamCallback callback = mock(CompletionStreamCallback.class);

        difyCompletionApi.sendCompletionMessageStream(request, callback);
        verify(callback, never()).onException(any(Exception.class));
    }
    @Test
    void stopCompletionMessage_shouldReturnResponse_whenServiceCallSucceeds() throws Exception {
        // Define mock response
        String mockResponseBody = "{\"result\": \"success\"}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        StopCompletionMessageResponse response = difyCompletionApi.stopCompletionMessage("taskId", StopCompletionMessageRequest.builder().user(user).build());
        System.out.println("stopCompletionMessage: "+response);
        assertNotNull(response);
        assertEquals("success", response.getResult());
    }


    @Test
    void feedbackMessage_shouldReturnResponse_whenServiceCallSucceeds() throws Exception {
        // Define mock response
        String mockResponseBody = "{\"result\": \"success\"}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        FeedBackRequest request = FeedBackRequest.builder()
                .user(user)
                .rating(RatingMode.LIKE)
                .build();
        LittleResponse response = difyCompletionApi.feedbackMessage(messageId, request);
        assertEquals("success", response.getResult());
    }
    @Test
    void getFeedBackList_shouldReturnResponse_whenServiceCallSucceeds() throws Exception {
        // Define mock response
        String mockResponseBody = "{\n" +
                "  \"limit\": 10,\n" +
                "  \"total\": 1,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": \"feedback-id-123\",\n" +
                "      \"rating\": \"like\",\n" +
                "      \"content\": \"Great service!\",\n" +
                "      \"user\": \"user-abc\",\n" +
                "      \"created_at\": 1672531199\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        FeedBackListResponse response = difyCompletionApi.getFeedBackList(1, 10);
        System.out.println(JSONUtils.toJson(response));
        assertNotNull(response);
        assertNotNull(response.getData());
    }

    @Test
    void createAnnotation_shouldReturnResponse_whenServiceCallSucceeds() throws Exception {
        // Define mock response
        String mockResponseBody = "{\n" +
                "  \"id\": \"anno-mock-id-123\",\n" +
                "  \"question\": \"What is Dify?\",\n" +
                "  \"answer\": \"Dify is a platform.\",\n" +
                "  \"created_at\": 1672531199,\n" +
                "  \"hit_count\": 0\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        AnnotationRequest request = AnnotationRequest.builder()
                .question("What is Dify?")
                .answer("Dify is a platform.")
                .build();
        Annotation response = difyCompletionApi.createAnnotation(request);
        System.out.println(response);
        assertNotNull(response.getId());
    }

    @Test
    void getAnnotationList_shouldReturnResponse_whenServiceCallSucceeds() throws Exception {
        // Define mock response
        String mockResponseBody = "{\n" +
                "  \"limit\": 10,\n" +
                "  \"total\": 1,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": \"anno-list-id-456\",\n" +
                "      \"question\": \"What is AI?\",\n" +
                "      \"answer\": \"AI is intelligence demonstrated by machines.\",\n" +
                "      \"created_at\": 1672531200,\n" +
                "      \"hit_count\": 5\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        AnnotationListResponse response = difyCompletionApi.getAnnotationList(1, 10);
        System.out.println(JSONUtils.toJson(response));
        assertNotNull(response.getData());
    }

    @Test
    void deleteAnnotation_shouldNotThrowException_whenServiceCallSucceeds() throws Exception {
        String mockResponseBody = "{\"result\": \"success\"}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json"));

        difyCompletionApi.deleteAnnotation("test-annotation-id");
    }

    @Test
    void updateAnnotation_shouldReturnResponse_whenServiceCallSucceeds() throws Exception {
        // Define mock response for updating an annotation
        String annotationIdToUpdate = "test-annotation-id-to-update";
        String mockResponseBody = "{\n" +
                "  \"id\": \"" + annotationIdToUpdate + "\",\n" +
                "  \"question\": \"What is Dify updated?\",\n" +
                "  \"answer\": \"Dify is an awesome platform.\",\n" +
                "  \"created_at\": 1672531201, \n" +
                "  \"hit_count\": 1\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // Assuming an annotation with this ID exists for update.
        AnnotationRequest request = AnnotationRequest.builder()
                .question("What is Dify updated?")
                .answer("Dify is an awesome platform.")
                .build();
        Annotation response = difyCompletionApi.updateAnnotation(annotationIdToUpdate, request);
        assertNotNull(response.getId());
        assertEquals(annotationIdToUpdate, response.getId());
    }
} 