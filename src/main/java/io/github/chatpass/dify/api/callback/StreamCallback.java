package io.github.chatpass.dify.api.callback;

import io.github.chatpass.dify.data.event.ErrorEvent;
import io.github.chatpass.dify.data.event.PingEvent;

public interface StreamCallback {
    /**
     * 错误事件
     */
    default void onError(ErrorEvent event) {
    }

    default void onException(Throwable throwable) {
    }

    /**
     * 完成事件
     */
    default void onComplete() {
    }

    /**
     * 心跳事件
     * @param event
     */
    default void onPing(PingEvent event) {
    }
}
