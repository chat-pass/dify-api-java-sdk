package io.github.chatpass.dify.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Workflow执行状态响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowRunStatusResponse {

    /**
     * workflow 执行 ID
     */
    private String id;

    /**
     * 关联的 Workflow ID
     */
    @JsonProperty("workflow_id")
    private String workflowId;

    /**
     * 执行状态
     * Available options: running, succeeded, failed, stopped
     */
    private String status;

    /**
     * 任务输入内容的 JSON 字符串
     */
    private String inputs;

    /**
     * 任务输出内容的 JSON 对象
     */
    private Object outputs;

    /**
     * 错误原因
     */
    private String error;

    /**
     * 任务执行总步数
     */
    @JsonProperty("total_steps")
    private Integer totalSteps;

    /**
     * 任务执行总 tokens
     */
    @JsonProperty("total_tokens")
    private Integer totalTokens;

    /**
     * 任务开始时间
     */
    @JsonProperty("created_at")
    private Integer createdAt;

    /**
     * 任务结束时间
     */
    @JsonProperty("finished_at")
    private Integer finishedAt;

    /**
     * 耗时(秒)
     */
    @JsonProperty("elapsed_time")
    private Double elapsedTime;
} 