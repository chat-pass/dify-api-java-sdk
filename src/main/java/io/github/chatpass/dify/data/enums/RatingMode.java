package io.github.chatpass.dify.data.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RatingMode {
    LIKE("like"),
    DISLIKE("dislike"),
    NULL("null");
    private final String value;

    @JsonValue
    public String getValue() {
        return value;
    }
}
