package io.github.chatpass.dify.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Conversation {
    /**
     * 会话 ID
     */
    private String id;

    /**
     * 会话名称
     */
    private String name;

    /**
     * 用户输入参数
     */
    private Map<String, Object> inputs;

    /**
     * 会话状态
     */
    private String status;

    /**
     * 开场白
     */
    private String introduction;

    /**
     * 创建时间
     */
    private Long createdAt;

    /**
     * 更新时间
     */
    private Long updatedAt;
}
