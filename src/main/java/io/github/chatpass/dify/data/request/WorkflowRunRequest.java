package io.github.chatpass.dify.data.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.chatpass.dify.data.enums.ResponseMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowRunRequest {
    
    /**
     * 允许传入 App 定义的各变量值。如果变量是文件列表类型，该变量对应的值应是 InputFileObjectWorkflowCn 对象的列表。
     * 示例:
     * {
     *   "user_query": "请帮我翻译这句话。",
     *   "target_language": "法语"
     * }
     */
    private Map<String, Object> inputs;

    /**
     * 返回响应模式。streaming (推荐) 基于 SSE；blocking 等待执行完毕后返回 (Cloudflare 100秒超时限制)。
     */
    @JsonProperty("response_mode")
    private ResponseMode responseMode;

    /**
     * 用户标识，应用内唯一。
     */
    private String user;
} 