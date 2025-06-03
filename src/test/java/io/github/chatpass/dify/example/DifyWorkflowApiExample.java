package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyWorkflowApi;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.request.SimpleUserRequest;
import io.github.chatpass.dify.data.request.WorkflowRunRequest;
import io.github.chatpass.dify.data.response.WorkflowLogListResponse;
import io.github.chatpass.dify.data.response.WorkflowRunResponse;
import io.github.chatpass.dify.data.response.WorkflowRunStatusResponse;
import io.github.chatpass.dify.data.response.WorkflowStopResponse;
import io.github.chatpass.dify.exception.DifyApiException;

import java.util.HashMap;
import java.util.Map;

public class DifyWorkflowApiExample {

    private static String workflowRunId;
    private static String taskId;
    private static DifyWorkflowApi difyWorkflowApi;

    public static void main(String[] args) {
        final Map<String, String> envs = System.getenv();
        difyWorkflowApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyWorkflowApi();

        try {
            runWorkflowExample();
            getWorkflowRunStatusExample();
            stopWorkflowExample();
            getworkflowlogsExample();
            getWorkflowLogsWithKeywordExample();
            getWorkflowLogsWithStatusExample();
            getWorkflowLogsWithAllParamsExample();
        } catch (DifyApiException e) {
            e.printStackTrace();
        }
    }

    private static void runWorkflowExample() {
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("query", "请帮我翻译这句话：Hello World");

        WorkflowRunRequest request = WorkflowRunRequest.builder()
                .inputs(inputs)
                .responseMode(ResponseMode.BLOCKING)
                .user("test_user_" + System.currentTimeMillis())
                .build();
        
        WorkflowRunResponse response = difyWorkflowApi.runWorkflow(request);
        System.out.println("执行workflow响应: " + response);
        
        // 保存workflowRunId和taskId用于后续测试
        workflowRunId = response.getWorkflowRunId();
        taskId = response.getTaskId();
    }

    private static void getWorkflowRunStatusExample() {
        WorkflowRunStatusResponse response = difyWorkflowApi.getWorkflowRunStatus(workflowRunId);
        System.out.println("获取workflow执行状态响应: " + response);
    }

    private static void stopWorkflowExample() {
        SimpleUserRequest request = SimpleUserRequest.builder()
                .user("test_user_" + System.currentTimeMillis())
                .build();

        WorkflowStopResponse response = difyWorkflowApi.stopWorkflow(taskId, request);
        System.out.println("停止workflow响应: " + response);
    }
    private static void getworkflowlogsExample() {
        WorkflowLogListResponse response = difyWorkflowApi.getWorkflowLogs(null, null, 1, 10);
        System.out.println("获取workflow日志列表响应: " + response);
    }

    private static void getWorkflowLogsWithKeywordExample() {
        WorkflowLogListResponse response = difyWorkflowApi.getWorkflowLogs("测试", null, 1, 5);
        System.out.println("带关键词的workflow日志列表响应: " + response);
    }

    private static void getWorkflowLogsWithStatusExample() {
        WorkflowLogListResponse response = difyWorkflowApi.getWorkflowLogs(null, "succeeded", 1, 10);
        System.out.println("带状态筛选的workflow日志列表响应: " + response);
    }

    private static void getWorkflowLogsWithAllParamsExample() {
        WorkflowLogListResponse response = difyWorkflowApi.getWorkflowLogs("翻译", "succeeded", 1, 5);
        System.out.println("带所有参数的workflow日志列表响应: " + response);
    }
}
