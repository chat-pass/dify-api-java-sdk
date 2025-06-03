package io.github.chatpass.dify.data.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.chatpass.dify.data.response.ChatMessageResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageEndEvent extends BaseMessageEvent{

    /**
     * 元数据
     */
    private ChatMessageResponse.Metadata metadata;

    /**
     * 模型用量信息
     */
    @JsonProperty("usage")
    private ChatMessageResponse.Usage usage;

    /**
     * 引用和归属分段列表
     */
    @JsonProperty("retriever_resources")
    private List<ChatMessageResponse.RetrieverResources> retrieverResources;

}
