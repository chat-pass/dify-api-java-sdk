package io.github.chatpass.dify.data.response.datasets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class DocumentResponse {

    /**
     * 文档信息
     */
    private Document document;

    /**
     * 批次号
     */
    private String batch;

    /**
     * 文档详细信息
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
        private DataSourceInfo dataSourceInfo;

        /**
         * 数据源详细信息字典
         */
        @JsonProperty("data_source_detail_dict")
        private DataSourceDetailDict dataSourceDetailDict;

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

        /**
         * 显示状态
         */
        @JsonProperty("display_status")
        private String displayStatus;

        /**
         * 字数
         */
        @JsonProperty("word_count")
        private Integer wordCount;

        /**
         * 命中次数
         */
        @JsonProperty("hit_count")
        private Integer hitCount;

        /**
         * 文档形式
         */
        @JsonProperty("doc_form")
        private String docForm;
    }

    /**
     * 数据源信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataSourceInfo {

        /**
         * 上传文件ID
         */
        @JsonProperty("upload_file_id")
        private String uploadFileId;
    }

    /**
     * 数据源详细信息字典
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataSourceDetailDict {

        /**
         * 上传文件信息
         */
        @JsonProperty("upload_file")
        private UploadFile uploadFile;
    }

    /**
     * 上传文件信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UploadFile {

        /**
         * 文件ID
         */
        private String id;

        /**
         * 文件名称
         */
        private String name;

        /**
         * 文件大小
         */
        private Long size;

        /**
         * 文件扩展名
         */
        private String extension;

        /**
         * MIME类型
         */
        @JsonProperty("mime_type")
        private String mimeType;

        /**
         * 创建者
         */
        @JsonProperty("created_by")
        private String createdBy;

        /**
         * 创建时间
         */
        @JsonProperty("created_at")
        private Double createdAt;
    }
} 