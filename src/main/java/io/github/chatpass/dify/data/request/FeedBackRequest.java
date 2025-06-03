package io.github.chatpass.dify.data.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.chatpass.dify.data.enums.RatingMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedBackRequest {
    private String user;
    private RatingMode rating;
    private String content;
}
