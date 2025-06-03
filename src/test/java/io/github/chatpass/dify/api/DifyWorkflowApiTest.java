package io.github.chatpass.dify.api;

import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.request.SimpleUserRequest;
import io.github.chatpass.dify.data.request.WorkflowRunRequest;
import io.github.chatpass.dify.data.response.WorkflowLogListResponse;
import io.github.chatpass.dify.data.response.WorkflowRunResponse;
import io.github.chatpass.dify.data.response.WorkflowRunStatusResponse;
import io.github.chatpass.dify.data.response.WorkflowStopResponse;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DifyWorkflowApiTest extends MockServerUtilsTest {

    @Test
    public void testRunWorkflow() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"workflow_run_id\": \"djflajgkldjgd\",\n" +
                "  \"task_id\": \"9da23599-e713-473b-982c-4328d4f5c78a\",\n" +
                "  \"data\": {\n" +
                "    \"id\": \"djflajgkldjgd\",\n" +
                "    \"workflow_id\": \"fbc33c9a-9acd-47b8-8ff5-5a7d5a7a46b8\",\n" +
                "    \"status\": \"running\",\n" +
                "    \"outputs\": null,\n" +
                "    \"error\": null,\n" +
                "    \"elapsed_time\": 0.875,\n" +
                "    \"total_tokens\": 3562,\n" +
                "    \"total_steps\": 8,\n" +
                "    \"created_at\": 1705407629,\n" +
                "    \"finished_at\": 1705407689\n" +
                "  }\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 创建请求对象
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("query", "请帮我翻译这句话：Hello World");
        inputs.put("target_language", "中文");

        WorkflowRunRequest request = WorkflowRunRequest.builder()
                .inputs(inputs)
                .responseMode(ResponseMode.BLOCKING)
                .user("test_user_123")
                .build();

        // 调用方法
        WorkflowRunResponse response = difyWorkflowApi.runWorkflow(request);

        // 验证响应
        assertNotNull(response);
        assertEquals("djflajgkldjgd", response.getWorkflowRunId());
        assertEquals("9da23599-e713-473b-982c-4328d4f5c78a", response.getTaskId());
        assertNotNull(response.getData());
        assertEquals("djflajgkldjgd", response.getData().getId());
        assertEquals("fbc33c9a-9acd-47b8-8ff5-5a7d5a7a46b8", response.getData().getWorkflowId());
        assertEquals("running", response.getData().getStatus());
    }

    @Test
    public void testGetWorkflowRunStatus() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"id\": \"djflajgkldjgd\",\n" +
                "  \"workflow_id\": \"fbc33c9a-9acd-47b8-8ff5-5a7d5a7a46b8\",\n" +
                "  \"status\": \"succeeded\",\n" +
                "  \"inputs\": \"{\\\"query\\\": \\\"请帮我翻译这句话：Hello World\\\"}\",\n" +
                "  \"outputs\": {\n" +
                "    \"text\": \"你好，世界\"\n" +
                "  },\n" +
                "  \"error\": null,\n" +
                "  \"total_steps\": 8,\n" +
                "  \"total_tokens\": 3562,\n" +
                "  \"created_at\": 1705407629,\n" +
                "  \"finished_at\": 1705407689,\n" +
                "  \"elapsed_time\": 60.0\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 调用方法
        String workflowRunId = "djflajgkldjgd";
        WorkflowRunStatusResponse response = difyWorkflowApi.getWorkflowRunStatus(workflowRunId);

        // 验证响应
        assertNotNull(response);
        assertEquals("djflajgkldjgd", response.getId());
        assertEquals("fbc33c9a-9acd-47b8-8ff5-5a7d5a7a46b8", response.getWorkflowId());
        assertEquals("succeeded", response.getStatus());
        assertEquals("{\"query\": \"请帮我翻译这句话：Hello World\"}", response.getInputs());
        assertNotNull(response.getOutputs());
        assertEquals(8, response.getTotalSteps().intValue());
        assertEquals(3562, response.getTotalTokens().intValue());
        assertEquals(60.0, response.getElapsedTime(), 0.01);
    }

    @Test
    public void testStopWorkflow() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"result\": \"success\"\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 创建请求对象
        SimpleUserRequest request = SimpleUserRequest.builder()
                .user("test_user_123")
                .build();

        // 调用方法
        String taskId = "9da23599-e713-473b-982c-4328d4f5c78a";
        WorkflowStopResponse response = difyWorkflowApi.stopWorkflow(taskId, request);

        // 验证响应
        assertNotNull(response);
        assertEquals("success", response.getResult());
    }

    @Test
    public void testGetWorkflowLogs() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"page\": 2,\n" +
                "  \"limit\": 5,\n" +
                "  \"total\": 15,\n" +
                "  \"has_more\": true,\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": \"filtered-log-1\",\n" +
                "      \"workflow_run\": {\n" +
                "        \"id\": \"filtered-run-1\",\n" +
                "        \"version\": \"2\",\n" +
                "        \"status\": \"succeeded\",\n" +
                "        \"error\": null,\n" +
                "        \"elapsed_time\": 3.0,\n" +
                "        \"total_tokens\": 2500,\n" +
                "        \"total_steps\": 10,\n" +
                "        \"created_at\": 1705407629,\n" +
                "        \"finished_at\": 1705407689\n" +
                "      },\n" +
                "      \"created_from\": \"api\",\n" +
                "      \"created_by_role\": \"end_user\",\n" +
                "      \"created_by_account\": null,\n" +
                "      \"created_by_end_user\": {\n" +
                "        \"id\": \"advanced-user\",\n" +
                "        \"type\": \"browser_session_id\",\n" +
                "        \"is_anonymous\": false,\n" +
                "        \"session_id\": \"advanced-session\"\n" +
                "      },\n" +
                "      \"created_at\": 1705407629\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 调用方法
        WorkflowLogListResponse response = difyWorkflowApi.getWorkflowLogs("测试", "succeeded", 2, 5);

        // 验证响应
        assertNotNull(response);
        assertEquals(2, response.getPage().intValue());
        assertEquals(5, response.getLimit().intValue());
        assertEquals(15, response.getTotal().intValue());
        assertTrue(response.getHasMore());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        
        WorkflowLogListResponse.WorkflowLogData logData = response.getData().get(0);
        assertEquals("filtered-log-1", logData.getId());
        assertEquals("succeeded", logData.getWorkflowRun().getStatus());
        assertEquals("2", logData.getWorkflowRun().getVersion());
        assertEquals(10, logData.getWorkflowRun().getTotalSteps().intValue());
    }
} 