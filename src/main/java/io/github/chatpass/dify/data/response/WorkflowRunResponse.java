package io.github.chatpass.dify.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowRunResponse {
    
    /**
     * 工作流运行 ID
     */
    @JsonProperty("workflow_run_id")
    private String workflowRunId;

    /**
     * 任务 ID
     */
    @JsonProperty("task_id")
    private String taskId;

    /**
     * 工作流运行数据
     */
    private WorkflowRunData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WorkflowRunData {
        
        /**
         * 工作流运行 ID
         */
        private String id;

        /**
         * 工作流 ID
         */
        @JsonProperty("workflow_id")
        private String workflowId;

        /**
         * 运行状态
         */
        private String status;

        /**
         * 输出结果
         */
        private Map<String, Object> outputs;

        /**
         * 错误信息
         */
        private String error;

        /**
         * 执行耗时（毫秒）
         */
        @JsonProperty("elapsed_time")
        private Long elapsedTime;

        /**
         * 总 token 数
         */
        @JsonProperty("total_tokens")
        private Long totalTokens;

        /**
         * 总步骤数
         */
        @JsonProperty("total_steps")
        private Integer totalSteps;

        /**
         * 创建时间戳
         */
        @JsonProperty("created_at")
        private Long createdAt;

        /**
         * 完成时间戳
         */
        @JsonProperty("finished_at")
        private Long finishedAt;
    }
} 