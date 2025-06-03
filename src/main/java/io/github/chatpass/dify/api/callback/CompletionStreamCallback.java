package io.github.chatpass.dify.api.callback;

import io.github.chatpass.dify.data.event.*;
import io.github.chatpass.dify.data.event.*;

/**
 * 文本生成流式响应的事件回调
 */
public interface CompletionStreamCallback extends StreamCallback{
    /**
     * 消息事件
     */
    default void onMessage(MessageEvent event) {
    }

    /**
     * 消息结束事件
     */
    default void onMessageEnd(MessageEndEvent event) {
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

    /**
     * 消息替换事件
     */
    default void onMessageReplace(MessageReplaceEvent event) {
    }
}
