package io.github.chatpass.dify.api;

import io.github.chatpass.dify.api.callback.StreamEventDispatcher;
import io.github.chatpass.dify.api.callback.WorkflowStreamCallback;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.request.SimpleUserRequest;
import io.github.chatpass.dify.data.request.WorkflowRunRequest;
import io.github.chatpass.dify.data.response.WorkflowLogListResponse;
import io.github.chatpass.dify.data.response.WorkflowRunResponse;
import io.github.chatpass.dify.data.response.WorkflowRunStatusResponse;
import io.github.chatpass.dify.data.response.WorkflowStopResponse;
import io.github.chatpass.dify.service.DifyWorkflowApiService;
import io.github.chatpass.dify.DIfyApiServiceGenerator;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import retrofit2.Call;

@Slf4j
public class DifyWorkflowApi extends BaseApi{
    private final DifyWorkflowApiService workflowApiService;

    public DifyWorkflowApi(String baseUrl, String apiKey) {
        super(baseUrl, apiKey);
        this.workflowApiService = createService(DifyWorkflowApiService.class);
    }

    public WorkflowRunResponse runWorkflow(WorkflowRunRequest request) {
        log.debug("run workflow, request={}", request);
        return DIfyApiServiceGenerator.executeSync(workflowApiService.runWorkflow(request));
    }

    public void runWorkflowStream(WorkflowRunRequest request, WorkflowStreamCallback callback) {
        log.debug("run stream workflow, request={}", request);
        request.setResponseMode(ResponseMode.STREAMING);
        Call<ResponseBody> call = workflowApiService.runWorkflowStream(request);
        DIfyApiServiceGenerator.executeStreamRequest(call, line -> DIfyApiServiceGenerator.processStreamLine(line, callback, (data, eventType) -> {
            StreamEventDispatcher.dispatchWorkflowEvent(callback, data);
        }), callback::onException);
    }

    /**
     * 获取workflow执行状态
     * @param workflowRunId workflow执行ID
     * @return workflow执行状态响应
     */
    public WorkflowRunStatusResponse getWorkflowRunStatus(String workflowRunId) {
        log.debug("get workflow run status, workflowRunId={}", workflowRunId);
        return DIfyApiServiceGenerator.executeSync(workflowApiService.getWorkflowRunStatus(workflowRunId));
    }

    /**
     * 停止workflow执行
     * @param taskId 任务ID，可在流式返回Chunk中获取
     * @param request 包含用户标识的请求体，必须和执行workflow接口传入的user保持一致
     * @return 停止操作响应
     */
    public WorkflowStopResponse stopWorkflow(String taskId, SimpleUserRequest request) {
        log.debug("stop workflow, taskId={}, request={}", taskId, request);
        return DIfyApiServiceGenerator.executeSync(workflowApiService.stopWorkflow(taskId, request));
    }

    /**
     * 获取workflow日志
     * @param keyword （可选）关键字
     * @param status （可选）执行状态：succeeded, failed, stopped, running
     * @param page （可选）当前页码，默认1
     * @param limit （可选）每页条数，默认20
     * @return workflow日志列表响应
     */
    public WorkflowLogListResponse getWorkflowLogs(String keyword, String status, Integer page, Integer limit) {
        log.debug("get workflow logs, keyword={}, status={}, page={}, limit={}", keyword, status, page, limit);
        return DIfyApiServiceGenerator.executeSync(workflowApiService.getWorkflowLogs(keyword, status, page, limit));
    }
}
