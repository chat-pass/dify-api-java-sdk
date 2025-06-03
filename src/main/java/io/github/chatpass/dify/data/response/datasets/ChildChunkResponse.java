package io.github.chatpass.dify.data.response.datasets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildChunkResponse {

    /**
     * 子分段数据
     */
    private ChildChunkData data;

    /**
     * 子分段数据详细信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ChildChunkData {

        /**
         * 子分段ID
         */
        private String id;

        /**
         * 分段ID
         */
        @JsonProperty("segment_id")
        private String segmentId;

        /**
         * 内容
         */
        private String content;

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