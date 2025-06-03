package io.github.chatpass.dify.data.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnnotationReplyAction {

    /**
     * 启用自动回复
     */
    ENABLE("enable"),

    /**
     * 关闭
     */
    DISABLE("disable");

    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
    
    
    
}
