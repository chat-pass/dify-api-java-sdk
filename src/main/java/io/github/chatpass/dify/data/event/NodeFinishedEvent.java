package io.github.chatpass.dify.data.event;

import io.github.chatpass.dify.data.enums.NodeFinishStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NodeFinishedEvent extends BaseWorkflowEvent {

    /**
     * 详细内容
     */
    private NodeFinishedData data;

    /**
     * node 执行结束事件数据
     */
    @Data
    @NoArgsConstructor
    public static class NodeFinishedData {

        /**
         * node 执行 ID
         */
        private String id;

        /**
         * 节点 ID
         */
        private String nodeId;

        /**
         * 执行序号，用于展示 Tracing Node 顺序
         */
        private Integer index;

        /**
         * 前置节点 ID，用于画布展示执行路径
         */
        private String predecessorNodeId;

        /**
         * 节点中所有使用到的前置节点变量内容
         */
        private Map<String, Object> inputs;

        /**
         * 节点过程数据
         */
        private Map<String, Object> processData;

        /**
         * 输出内容
         */
        private Map<String, Object> outputs;

        /**
         * 执行状态
         */
        private NodeFinishStatus status;

        /**
         * 错误原因
         */
        private String error;

        /**
         * 耗时(s)
         */
        private Double elapsedTime;

        /**
         * 元数据
         */
        private Map<String, Object> executionMetadata;

        /**
         * 总使用 tokens
         */
        private Integer totalTokens;

        /**
         * 总费用
         */
        private Double totalPrice;

        /**
         * 货币，如 USD / RMB
         */
        private String currency;

        /**
         * 开始时间
         */
        private Long createdAt;
    }
}
