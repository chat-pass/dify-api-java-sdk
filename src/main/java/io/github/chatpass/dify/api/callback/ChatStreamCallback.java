package io.github.chatpass.dify.api.callback;

import io.github.chatpass.dify.data.event.*;
import io.github.chatpass.dify.data.event.*;

public interface ChatStreamCallback extends StreamCallback{
    /**
     * 接收到消息
     * @param event
     */
    default void onMessage(MessageEvent event) {
    }

    /**
     * 消息结束事件
     * @param event
     */
    default void onMessageEnd(MessageEndEvent event) {
    }

    /**
     * 接收到文件
     * @param event
     */
    default void onMessageFile(MessageFileEvent event) {
    }

    default void onTTSMessage(TtsMessageEvent event) {
    }

    default void onTTSMessageEnd(TtsMessageEndEvent event) {
    }

    /**
     * 消息内容替换事件
     * @param event
     */
    default void onMessageReplace(MessageReplaceEvent event) {
    }

    default void onAgentMessage(AgentMessageEvent event) {
    }

    default void onAgentThought(AgentThoughtEvent event) {
    }
}
