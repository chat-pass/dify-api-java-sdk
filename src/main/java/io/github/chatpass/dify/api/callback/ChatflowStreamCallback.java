package io.github.chatpass.dify.api.callback;

import io.github.chatpass.dify.data.event.NodeFinishedEvent;
import io.github.chatpass.dify.data.event.NodeStartedEvent;
import io.github.chatpass.dify.data.event.WorkflowFinishedEvent;
import io.github.chatpass.dify.data.event.WorkflowStartedEvent;

/**
 * 工作流编排对话型应用流式回调接口
 */
public interface ChatflowStreamCallback extends ChatStreamCallback {
    /**
     * 工作流开始事件
     */
    default void onWorkflowStarted(WorkflowStartedEvent event) {
    }

    /**
     * 节点开始事件
     */
    default void onNodeStarted(NodeStartedEvent event) {
    }

    /**
     * 节点完成事件
     */
    default void onNodeFinished(NodeFinishedEvent event) {
    }

    /**
     * 工作流完成事件
     */
    default void onWorkflowFinished(WorkflowFinishedEvent event) {
    }
}
