package io.github.chatpass.dify.data.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 消息事件的基类
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class BaseMessageEvent extends BaseEvent {

    /**
     * 消息唯一ID
     */
    private String id;

    /**
     * 会话ID（对话型应用特有）
     */
    private String conversationId;
}
