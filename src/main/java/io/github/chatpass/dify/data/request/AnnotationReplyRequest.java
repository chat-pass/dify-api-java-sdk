package io.github.chatpass.dify.data.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnnotationReplyRequest {

    /**
     * 指定的嵌入模型提供商, 必须先在系统内设定好接入的模型，对应的是provider字段
     */
    private String embeddingProviderName;

    /**
     * 指定的嵌入模型，对应的是model字段
     */
    private String embeddingModelName;

    /**
     * 相似度阈值，当相似度大于该阈值时，系统会自动回复，否则不回复
     */
    private double scoreThreshold;
    
    
}
