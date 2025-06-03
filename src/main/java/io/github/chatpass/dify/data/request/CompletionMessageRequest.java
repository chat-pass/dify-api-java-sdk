package io.github.chatpass.dify.data.request;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.chatpass.dify.data.enums.ResponseMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompletionMessageRequest {

     /**
     * 输入参数，允许传入 App 定义的各变量值, 
     * query 为必填 用户输入的文本内容。
     * 
     *  "inputs": {"query": "Hello, world!"}
     */
    private Map<String, Object> inputs;

    /**
     * 响应模式
     */
    private ResponseMode responseMode;

    /**
     * 用户标识
     */
    private String user;

    /**
     * 文件列表
     */
    private List<FileInfo> files;

}
