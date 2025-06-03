package io.github.chatpass.dify.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedBackListResponse {

    private List<FeedBack> data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FeedBack{
        private String id;

        private String appId;

        private String conversationId;

        private String messageId;

        private String rating;

        private String content;

        private String fromSource;

        private String fromEndUserId;

        private String fromAccountId;

        private Date createdAt;

        private Date updatedAt;
    }
}
