package io.github.chatpass.dify.data.response.datasets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class EmbeddingModelListResponse {

    /**
     * 模型提供商数据列表
     */
    private List<ModelProvider> data;

    /**
     * 模型提供商信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ModelProvider {

        /**
         * 提供商名称
         */
        private String provider;

        /**
         * 提供商标签（多语言）
         */
        private Map<String, String> label;

        /**
         * 小图标URL（多语言）
         */
        @JsonProperty("icon_small")
        private Map<String, String> iconSmall;

        /**
         * 大图标URL（多语言）
         */
        @JsonProperty("icon_large")
        private Map<String, String> iconLarge;

        /**
         * 提供商状态
         */
        private String status;

        /**
         * 模型列表
         */
        private List<Model> models;
    }

    /**
     * 模型信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Model {

        /**
         * 模型名称
         */
        private String model;

        /**
         * 模型标签（多语言）
         */
        private Map<String, String> label;

        /**
         * 模型类型
         */
        @JsonProperty("model_type")
        private String modelType;

        /**
         * 模型特性
         */
        private Object features;

        /**
         * 获取来源
         */
        @JsonProperty("fetch_from")
        private String fetchFrom;

        /**
         * 模型属性
         */
        @JsonProperty("model_properties")
        private ModelProperties modelProperties;

        /**
         * 是否已弃用
         */
        private Boolean deprecated;

        /**
         * 模型状态
         */
        private String status;

        /**
         * 是否启用负载均衡
         */
        @JsonProperty("load_balancing_enabled")
        private Boolean loadBalancingEnabled;
    }

    /**
     * 模型属性
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ModelProperties {

        /**
         * 上下文大小
         */
        @JsonProperty("context_size")
        private Integer contextSize;
    }
} 