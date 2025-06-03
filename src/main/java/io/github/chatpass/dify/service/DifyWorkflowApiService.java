package io.github.chatpass.dify.service;

import io.github.chatpass.dify.data.request.SimpleUserRequest;
import io.github.chatpass.dify.data.request.WorkflowRunRequest;
import io.github.chatpass.dify.data.response.WorkflowLogListResponse;
import io.github.chatpass.dify.data.response.WorkflowRunResponse;
import io.github.chatpass.dify.data.response.WorkflowRunStatusResponse;
import io.github.chatpass.dify.data.response.WorkflowStopResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface DifyWorkflowApiService {

    @POST("workflows/run")
    Call<WorkflowRunResponse> runWorkflow(@Body WorkflowRunRequest request);

    @POST("workflows/run")
    @Streaming
    Call<ResponseBody> runWorkflowStream(@Body WorkflowRunRequest request);

    /**
     * 获取workflow执行情况
     * @param workflowRunId workflow执行ID
     * @return workflow执行状态响应
     */
    @GET("workflows/run/{workflow_run_id}")
    Call<WorkflowRunStatusResponse> getWorkflowRunStatus(@Path("workflow_run_id") String workflowRunId);

    /**
     * 停止workflow执行
     * @param taskId 任务ID，可在流式返回Chunk中获取
     * @param request 包含用户标识的请求体，必须和执行workflow接口传入的user保持一致
     * @return 停止操作响应
     */
    @POST("workflows/tasks/{task_id}/stop")
    Call<WorkflowStopResponse> stopWorkflow(@Path("task_id") String taskId, @Body SimpleUserRequest request);

    /**
     * 获取workflow日志
     * @param keyword （可选）关键字
     * @param status （可选）执行状态：succeeded, failed, stopped, running
     * @param page （可选）当前页码，默认1
     * @param limit （可选）每页条数，默认20
     * @return workflow日志列表响应
     */
    @GET("workflows/logs")
    Call<WorkflowLogListResponse> getWorkflowLogs(
            @Query("keyword") String keyword,
            @Query("status") String status,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );
}
