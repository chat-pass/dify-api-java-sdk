package io.github.chatpass.dify.data.request.datasets;

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
public class UpdateDocumentMetadataRequest {

    /**
     * 操作数据列表
     */
    @JsonProperty("operation_data")
    private List<OperationData> operationData;

    /**
     * 操作数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OperationData {

        /**
         * 文档ID
         */
        @JsonProperty("document_id")
        private String documentId;

        /**
         * 元数据列表
         */
        @JsonProperty("metadata_list")
        private List<MetadataItem> metadataList;
    }

    /**
     * 元数据项
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetadataItem {

        /**
         * 元数据ID
         */
        private String id;

        /**
         * 元数据值
         */
        private String value;

        /**
         * 元数据名称
         */
        private String name;
    }
} 