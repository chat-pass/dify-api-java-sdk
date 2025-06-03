package io.github.chatpass.dify.data.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatCompletionResponse {

    /**
     * 消息唯一 ID
     */
    private String messageId;

    /**
     * App 模式，固定为 chat
     */
    private String mode;

    /**
     * 完整回复内容
     */
    private String answer;

    private ChatMessageResponse.Metadata metadata;

    /**
     * 消息创建时间戳
     */
    private Long createdAt;

    
}