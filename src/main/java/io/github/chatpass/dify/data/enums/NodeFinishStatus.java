package io.github.chatpass.dify.data.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NodeFinishStatus {
    RUNNING("running"),
    SUCCESSED("succeeded"),
    STOPPED("stopped"),
    FAILED("failed");
    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
