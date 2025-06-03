package io.github.chatpass.dify.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessageListResponse {
    /**
     * 返回条数
     */
    private Integer limit;

    /**
     * 是否存在下一页
     */
    private Boolean hasMore;

    /**
     * 消息列表
     */
    private List<Message> data;

    /**
     * 消息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        /**
         * 消息 ID
         */
        private String id;

        /**
         * 会话 ID
         */
        private String conversationId;

        /**
         * 用户输入参数
         */
        private Map<String, Object> inputs;

        /**
         * 用户输入/提问内容
         */
        private String query;

        /**
         * 回答内容
         */
        private String answer;

        /**
         * 消息文件
         */
        private List<MessageFile> messageFiles;

        /**
         * 反馈信息
         */
        private Feedback feedback;

        /**
         * 引用和归属分段列表
         */
        private List<ChatMessageResponse.RetrieverResources> retrieverResources;

        /**
         * Agent 思考内容（仅 Agent 模式下不为空）
         */
        private List<AgentThought> agentThoughts;

        /**
         * 创建时间
         */
        private Long createdAt;
    }

    /**
     * 消息文件
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MessageFile {
        /**
         * 文件 ID
         */
        private String id;

        /**
         * 文件类型
         */
        private String type;

        /**
         * 文件 URL
         */
        private String url;

        /**
         * 文件归属
         */
        private String belongsTo;
    }

    /**
     * 反馈信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Feedback {
        /**
         * 评分
         */
        private String rating;
    }

    /**
     * Agent 思考内容
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AgentThought {
        /**
         * 思考 ID
         */
        private String id;

        /**
         * 链 ID
         */
        private String chainId;

        /**
         * 消息 ID
         */
        private String messageId;

        /**
         * 位置
         */
        private Integer position;

        /**
         * 思考内容
         */
        private String thought;

        /**
         * 工具
         */
        private String tool;

        /**
         * 工具输入
         */
        private String toolInput;

        /**
         * 创建时间
         */
        private Long createdAt;

        /**
         * 观察结果
         */
        private String observation;

        /**
         * 消息文件
         */
        private List<String> messageFiles;
    }
}
