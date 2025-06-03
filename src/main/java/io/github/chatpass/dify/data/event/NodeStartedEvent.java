package io.github.chatpass.dify.data.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 工作流node开始执行
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NodeStartedEvent extends BaseWorkflowEvent{
    /**
     * 详细内容
     */
    private NodeStartedData data;

    /**
     * node 开始执行事件数据
     */
    @Data
    @NoArgsConstructor
    public static class NodeStartedData {

        /**
         * workflow 执行 ID
         */
        private String id;

        /**
         * 节点 ID
         */
        private String nodeId;

        /**
         * 节点类型
         */
        private String nodeType;

        /**
         * 节点名称
         */
        private String title;

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
         * 开始时间
         */
        private Long createdAt;
    }
}
