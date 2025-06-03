package io.github.chatpass.dify.data.request.datasets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDocumentByFileRequest {

    /**
     * 源文档 ID（选填）
     * 用于重新上传文档或修改文档清洗、分段配置，缺失的信息从源文档复制
     * 源文档不可为归档的文档
     * 当传入 original_document_id 时，代表文档进行更新操作，process_rule 为可填项目，不填默认使用源文档的分段方式
     * 未传入 original_document_id 时，代表文档进行新增操作，process_rule 为必填
     */
    private String originalDocumentId;

    /**
     * 索引方式
     * high_quality 高质量：使用 embedding 模型进行嵌入，构建为向量数据库索引
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
     * 检索模式（当知识库未设置任何参数的时候，首次上传需要提供）
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