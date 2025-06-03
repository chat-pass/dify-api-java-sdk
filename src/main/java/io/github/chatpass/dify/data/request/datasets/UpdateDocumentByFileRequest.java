package io.github.chatpass.dify.data.request.datasets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDocumentByFileRequest {

    /**
     * 文档名称（选填）
     */
    private String name;


    /**
     * 处理规则（选填）
     */
    private ProcessRule processRule;
} 