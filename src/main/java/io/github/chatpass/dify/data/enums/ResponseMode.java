package io.github.chatpass.dify.data.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMode {
    /**
     * 流式模式（推荐）
     */
    STREAMING("streaming"),

    /**
     * 阻塞模式，等待执行完毕后返回结果。（请求若流程较长可能会被中断）。
     * 由于 Cloudflare 限制，请求会在 100 秒超时无返回后中断。
     */
    BLOCKING("blocking");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
