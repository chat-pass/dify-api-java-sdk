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
public class DatasetResponse {

    /**
     * 数据集ID
     */
    private String id;

    /**
     * 数据集名称
     */
    private String name;

    /**
     * 数据集描述
     */
    private String description;

    /**
     * 提供商
     */
    private String provider;

    /**
     * 权限
     */
    private String permission;

    /**
     * 数据源类型
     */
    @JsonProperty("data_source_type")
    private String dataSourceType;

    /**
     * 索引技术
     */
    @JsonProperty("indexing_technique")
    private String indexingTechnique;

    /**
     * 应用数量
     */
    @JsonProperty("app_count")
    private Integer appCount;

    /**
     * 文档数量
     */
    @JsonProperty("document_count")
    private Integer documentCount;

    /**
     * 字数
     */
    @JsonProperty("word_count")
    private Integer wordCount;

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
     * 更新者
     */
    @JsonProperty("updated_by")
    private String updatedBy;

    /**
     * 更新时间
     */
    @JsonProperty("updated_at")
    private Long updatedAt;

    /**
     * 嵌入模型
     */
    @JsonProperty("embedding_model")
    private String embeddingModel;

    /**
     * 嵌入模型提供商
     */
    @JsonProperty("embedding_model_provider")
    private String embeddingModelProvider;

    /**
     * 嵌入是否可用
     */
    @JsonProperty("embedding_available")
    private Boolean embeddingAvailable;

    /**
     * 检索模型配置
     */
    @JsonProperty("retrieval_model_dict")
    private RetrievalModelDict retrievalModelDict;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 文档形式
     */
    @JsonProperty("doc_form")
    private String docForm;

    /**
     * 外部知识信息
     */
    @JsonProperty("external_knowledge_info")
    private ExternalKnowledgeInfo externalKnowledgeInfo;

    /**
     * 外部检索模型
     */
    @JsonProperty("external_retrieval_model")
    private ExternalRetrievalModel externalRetrievalModel;


    /**
     * 检索模型配置
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RetrievalModelDict {

        /**
         * 搜索方法
         */
        @JsonProperty("search_method")
        private String searchMethod;

        /**
         * 重排序是否启用
         */
        @JsonProperty("reranking_enable")
        private Boolean rerankingEnable;

        /**
         * 重排序模式
         */
        @JsonProperty("reranking_mode")
        private String rerankingMode;

        /**
         * 重排序模型
         */
        @JsonProperty("reranking_model")
        private RerankingModel rerankingModel;

        /**
         * 权重配置
         */
        private Weights weights;

        /**
         * Top K
         */
        @JsonProperty("top_k")
        private Integer topK;

        /**
         * 分数阈值是否启用
         */
        @JsonProperty("score_threshold_enabled")
        private Boolean scoreThresholdEnabled;

        /**
         * 分数阈值
         */
        @JsonProperty("score_threshold")
        private Double scoreThreshold;
    }

    /**
     * 重排序模型
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RerankingModel {

        /**
         * 重排序提供商名称
         */
        @JsonProperty("reranking_provider_name")
        private String rerankingProviderName;

        /**
         * 重排序模型名称
         */
        @JsonProperty("reranking_model_name")
        private String rerankingModelName;
    }

    /**
     * 权重配置
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weights {

        /**
         * 权重类型
         */
        @JsonProperty("weight_type")
        private String weightType;

        /**
         * 关键词设置
         */
        @JsonProperty("keyword_setting")
        private KeywordSetting keywordSetting;

        /**
         * 向量设置
         */
        @JsonProperty("vector_setting")
        private VectorSetting vectorSetting;
    }

    /**
     * 关键词设置
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class KeywordSetting {

        /**
         * 关键词权重
         */
        @JsonProperty("keyword_weight")
        private Double keywordWeight;
    }

    /**
     * 向量设置
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VectorSetting {

        /**
         * 向量权重
         */
        @JsonProperty("vector_weight")
        private Double vectorWeight;

        /**
         * 嵌入模型名称
         */
        @JsonProperty("embedding_model_name")
        private String embeddingModelName;

        /**
         * 嵌入提供商名称
         */
        @JsonProperty("embedding_provider_name")
        private String embeddingProviderName;
    }

    /**
     * 外部知识信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExternalKnowledgeInfo {

        /**
         * 外部知识ID
         */
        @JsonProperty("external_knowledge_id")
        private String externalKnowledgeId;

        /**
         * 外部知识API ID
         */
        @JsonProperty("external_knowledge_api_id")
        private String externalKnowledgeApiId;

        /**
         * 外部知识API名称
         */
        @JsonProperty("external_knowledge_api_name")
        private String externalKnowledgeApiName;

        /**
         * 外部知识API端点
         */
        @JsonProperty("external_knowledge_api_endpoint")
        private String externalKnowledgeApiEndpoint;
    }

    /**
     * 外部检索模型
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExternalRetrievalModel {

        /**
         * Top K
         */
        @JsonProperty("top_k")
        private Integer topK;

        /**
         * 分数阈值
         */
        @JsonProperty("score_threshold")
        private Double scoreThreshold;

        /**
         * 分数阈值是否启用
         */
        @JsonProperty("score_threshold_enabled")
        private Boolean scoreThresholdEnabled;
    }
} 