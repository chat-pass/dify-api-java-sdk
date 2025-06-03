package io.github.chatpass.dify.api.callback;

import io.github.chatpass.dify.data.event.*;
import io.github.chatpass.dify.data.event.*;

/**
 * Workflow 流式响应回调接口
 */
public interface WorkflowStreamCallback extends StreamCallback {
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

    /**
     * 工作流LLM执行过程
     */
    default void onWorkflowTextChunk(WorkflowTextChunkEvent event){
    }

    /**
     * TTS 消息事件
     */
    default void onTtsMessage(TtsMessageEvent event) {
    }

    /**
     * TTS 消息结束事件
     */
    default void onTtsMessageEnd(TtsMessageEndEvent event) {
    }

}
