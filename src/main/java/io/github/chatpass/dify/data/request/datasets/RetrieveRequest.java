package io.github.chatpass.dify.data.request.datasets;

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
public class RetrieveRequest {

    /**
     * 检索关键词
     */
    private String query;

    /**
     * 检索参数（选填，如不填，按照默认方式召回）
     */
    @JsonProperty("retrieval_model")
    private RetrievalModel retrievalModel;

    /**
     * 未启用字段
     */
    @JsonProperty("external_retrieval_model")
    private Object externalRetrievalModel;

    /**
     * 检索模型配置
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RetrievalModel {

        /**
         * 检索方法：keyword_search、semantic_search、full_text_search、hybrid_search
         */
        @JsonProperty("search_method")
        private String searchMethod;

        /**
         * 是否启用 Reranking
         */
        @JsonProperty("reranking_enable")
        private Boolean rerankingEnable;

        /**
         * Rerank 模型配置
         */
        @JsonProperty("reranking_mode")
        private Object rerankingMode;

        /**
         * Rerank 模型
         */
        @JsonProperty("reranking_model")
        private RerankingModel rerankingModel;

        /**
         * 混合检索模式下语意检索的权重设置
         */
        private Float weights;

        /**
         * 返回结果数量
         */
        @JsonProperty("top_k")
        private Integer topK;

        /**
         * 是否开启 score 阈值
         */
        @JsonProperty("score_threshold_enabled")
        private Boolean scoreThresholdEnabled;

        /**
         * Score 阈值
         */
        @JsonProperty("score_threshold")
        private Float scoreThreshold;
    }

    /**
     * Rerank 模型配置
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RerankingModel {

        /**
         * Rerank 模型提供商
         */
        @JsonProperty("reranking_provider_name")
        private String rerankingProviderName;

        /**
         * Rerank 模型名称
         */
        @JsonProperty("reranking_model_name")
        private String rerankingModelName;
    }
} 