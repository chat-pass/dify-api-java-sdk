package io.github.chatpass.dify.data.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageFileEvent extends BaseEvent {
    /**
     * 文件唯一ID
     */
    private String id;

    /**
     * 文件类型，目前仅为image
     */
    private String type;

    /**
     * 文件归属，user或assistant，该接口返回仅为 assistant
     */
    private String belongsTo;

    /**
     * 文件访问地址
     */
    private String url;

    /**
     * 会话ID
     */
    private String conversationId;
}
