package io.github.chatpass.dify.data.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * workflow 开始执行事件
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WorkflowStartedEvent extends BaseWorkflowEvent {

    /**
     * 详细内容
     */
    private WorkflowStartedData data;

    /**
     * workflow 开始执行事件数据
     */
    @Data
    @NoArgsConstructor
    public static class WorkflowStartedData {

        /**
         * workflow 执行 ID
         */
        private String id;

        /**
         * 关联 Workflow ID
         */
        private String workflowId;

        /**
         * 自增序号，App 内自增，从 1 开始
         */
        private Integer sequenceNumber;

        /**
         * 开始时间
         */
        private Long createdAt;
    }
}
