package io.github.chatpass.dify.data.response.datasets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateSegmentResponse {

    /**
     * 分段数据列表
     */
    private List<SegmentData> data;

    /**
     * 文档形式
     */
    @JsonProperty("doc_form")
    private String docForm;

    /**
     * 分段数据详细信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SegmentData {

        /**
         * 分段ID
         */
        private String id;

        /**
         * 位置
         */
        private Integer position;

        /**
         * 文档ID
         */
        @JsonProperty("document_id")
        private String documentId;

        /**
         * 内容
         */
        private String content;

        /**
         * 答案
         */
        private String answer;

        /**
         * 字数
         */
        @JsonProperty("word_count")
        private Integer wordCount;

        /**
         * 令牌数
         */
        private Integer tokens;

        /**
         * 关键字
         */
        private List<String> keywords;

        /**
         * 索引节点ID
         */
        @JsonProperty("index_node_id")
        private String indexNodeId;

        /**
         * 索引节点哈希
         */
        @JsonProperty("index_node_hash")
        private String indexNodeHash;

        /**
         * 命中次数
         */
        @JsonProperty("hit_count")
        private Integer hitCount;

        /**
         * 是否启用
         */
        private Boolean enabled;

        /**
         * 禁用时间
         */
        @JsonProperty("disabled_at")
        private Long disabledAt;

        /**
         * 禁用者
         */
        @JsonProperty("disabled_by")
        private String disabledBy;

        /**
         * 状态
         */
        private String status;

        /**
         * 创建者
         */
        @JsonProperty("created_by")
        private String createdBy;

        /**
         * 创建时间
         */
        @JsonProperty("created_at")
        private Long createdAt;

        /**
         * 索引时间
         */
        @JsonProperty("indexing_at")
        private Long indexingAt;

        /**
         * 完成时间
         */
        @JsonProperty("completed_at")
        private Long completedAt;

        /**
         * 错误信息
         */
        private String error;

        /**
         * 停止时间
         */
        @JsonProperty("stopped_at")
        private Long stoppedAt;
    }
} 