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
public class DatasetMetadataResponse {

    /**
     * 文档元数据列表
     */
    @JsonProperty("doc_metadata")
    private List<DocMetadata> docMetadata;

    /**
     * 内置字段是否启用
     */
    @JsonProperty("built_in_field_enabled")
    private Boolean builtInFieldEnabled;

    /**
     * 文档元数据详细信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocMetadata {

        /**
         * 元数据ID
         */
        private String id;

        /**
         * 元数据名称
         */
        private String name;

        /**
         * 元数据类型
         */
        private String type;

        /**
         * 使用次数
         */
        private Integer count;
    }
} 