package io.github.chatpass.dify.data.request.datasets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDocumentByTextRequest {
    
    /**
     * 文档名称
     */
    private String name;
    
    /**
     * 文档内容
     */
    private String text;
    
    /**
     * 处理规则
     */
    private ProcessRule processRule;

} 