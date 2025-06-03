package io.github.chatpass.dify.data.request.datasets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDocumentByTextRequest {
    
    /**
     * 文档名称
     */
    private String name;
    
    /**
     * 文档内容
     */
    private String text;
    
    /**
     * 索引方式
     * high_quality 高质量：使用 ding 模型进行嵌入，构建为向量数据库索引
     * economy 经济：使用 keyword table index 的倒排索引进行构建
     */
    private String indexingTechnique;
    
    /**
     * 索引内容的形式
     * text_model text 文档直接 embedding，经济模式默认为该模式
     * hierarchical_model parent-child 模式
     * qa_model Q&A 模式：为分片文档生成 Q&A 对，然后对问题进行 embedding
     */
    private String docForm;
    
    /**
     * 在 Q&A 模式下，指定文档的语言，例如：English、Chinese
     */
    private String docLanguage;
    
    /**
     * 处理规则
     */
    private ProcessRule processRule;
    
    /**
     * 检索模式
     */
    private RetrievalModel retrievalModel;
    
    /**
     * Embedding 模型名称
     */
    private String embeddingModel;
    
    /**
     * Embedding 模型供应商
     */
    private String embeddingModelProvider;
} 