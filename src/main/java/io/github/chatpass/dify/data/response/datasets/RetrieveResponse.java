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
public class RetrieveResponse {

    /**
     * 查询信息
     */
    private Query query;

    /**
     * 检索记录列表
     */
    private List<Record> records;

    /**
     * 查询信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Query {

        /**
         * 查询内容
         */
        private String content;
    }

    /**
     * 检索记录
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Record {

        /**
         * 分段信息
         */
        private Segment segment;

        /**
         * 相关性得分
         */
        private Double score;

        /**
         * TSNE位置
         */
        @JsonProperty("tsne_position")
        private Object tsnePosition;
    }

    /**
     * 分段信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Segment {

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

        /**
         * 文档信息
         */
        private Document document;
    }

    /**
     * 文档信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Document {

        /**
         * 文档ID
         */
        private String id;

        /**
         * 数据源类型
         */
        @JsonProperty("data_source_type")
        private String dataSourceType;

        /**
         * 文档名称
         */
        private String name;
    }
} 