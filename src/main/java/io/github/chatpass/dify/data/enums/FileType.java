package io.github.chatpass.dify.data.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileType {
    /**
     * 文档类型
     */
    DOCUMENT("document"),

    /**
     * 图片类型
     */
    IMAGE("image"),

    /**
     * 音频类型
     */
    AUDIO("audio"),

    /**
     * 视频类型
     */
    VIDEO("video"),

    /**
     * 自定义类型
     */
    CUSTOM("custom");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
