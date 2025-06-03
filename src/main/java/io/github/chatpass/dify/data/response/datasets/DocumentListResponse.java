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
public class DocumentListResponse {

    /**
     * 文档列表数据
     */
    private List<DocumentInfo> data;

    /**
     * 是否有更多数据
     */
    @JsonProperty("has_more")
    private Boolean hasMore;

    /**
     * 每页限制数量
     */
    private Integer limit;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 页码
     */
    private Integer page;

    /**
     * 文档项信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocumentInfo {

        /**
         * 文档ID
         */
        private String id;

        /**
         * 位置
         */
        private Integer position;

        /**
         * 数据源类型
         */
        @JsonProperty("data_source_type")
        private String dataSourceType;

        /**
         * 数据源信息
         */
        @JsonProperty("data_source_info")
        private Object dataSourceInfo;

        /**
         * 数据集处理规则ID
         */
        @JsonProperty("dataset_process_rule_id")
        private String datasetProcessRuleId;

        /**
         * 文档名称
         */
        private String name;

        /**
         * 创建来源
         */
        @JsonProperty("created_from")
        private String createdFrom;

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
         * 令牌数
         */
        private Integer tokens;

        /**
         * 索引状态
         */
        @JsonProperty("indexing_status")
        private String indexingStatus;

        /**
         * 错误信息
         */
        private String error;

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
         * 是否归档
         */
        private Boolean archived;
    }
} 