package io.github.chatpass.dify.data.request.datasets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetrievalModel {
    /**
     * 检索方法
     * hybrid_search 混合检索
     * semantic_search 语义检索
     * full_text_search 全文检索
     */
    private String searchMethod;
    
    /**
     * 是否开启rerank
     */
    private Boolean rerankingEnable;
    
    /**
     * Rerank 模型配置
     */
    private RerankingModel rerankingModel;
    
    /**
     * 召回条数
     */
    private Integer topK;
    
    /**
     * 是否开启召回分数限制
     */
    private Boolean scoreThresholdEnabled;
    
    /**
     * 召回分数限制
     */
    private Float scoreThreshold;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RerankingModel {
        /**
         * Rerank 模型的提供商
         */
        private String rerankingProviderName;
        
        /**
         * Rerank 模型的名称
         */
        private String rerankingModelName;
    }
} 