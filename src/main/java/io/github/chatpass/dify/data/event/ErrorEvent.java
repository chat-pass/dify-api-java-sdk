package io.github.chatpass.dify.data.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 错误事件
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ErrorEvent extends BaseMessageEvent {

    /**
     * HTTP 状态码
     */
    private Integer status;

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误消息
     */
    private String message;
}
