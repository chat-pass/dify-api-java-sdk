package io.github.chatpass.dify.data.event;

import io.github.chatpass.dify.data.enums.NodeFinishStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * workflow 执行结束事件
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WorkflowFinishedEvent extends BaseWorkflowEvent {

    /**
     * 详细内容
     */
    private WorkflowFinishedData data;

    /**
     * workflow 执行结束事件数据
     */
    @Data
    @NoArgsConstructor
    public static class WorkflowFinishedData {

        /**
         * workflow 执行 ID
         */
        private String id;

        /**
         * 关联 Workflow ID
         */
        private String workflowId;

        /**
         * 执行状态
         */
        private NodeFinishStatus status;

        /**
         * 输出内容
         */
        private Map<String, Object> outputs;

        /**
         * 错误原因
         */
        private String error;

        /**
         * 耗时(s)
         */
        private Double elapsedTime;

        /**
         * 总使用 tokens
         */
        private Integer totalTokens;

        /**
         * 总步数（冗余），默认 0
         */
        private Integer totalSteps;

        /**
         * 开始时间
         */
        private Long createdAt;

        /**
         * 结束时间
         */
        private Long finishedAt;
    }
}
