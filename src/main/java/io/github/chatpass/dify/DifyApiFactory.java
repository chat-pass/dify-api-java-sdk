package io.github.chatpass.dify;

import io.github.chatpass.dify.api.*;
import io.github.chatpass.dify.util.ValidationUtils;

/**
 * Dify API 工厂类
 *
 * @author chat-pass
 */
public class DifyApiFactory {
    private final String baseUrl;
    private final String apiKey;

    private DifyApiFactory(String baseUrl, String apiKey) {
        this.baseUrl = ValidationUtils.validateNonBlank(baseUrl, "baseUrl");
        this.apiKey = ValidationUtils.validateNonBlank(apiKey, "apiKey");
    }

    /**
     * 创建新的API工厂实例
     *
     * @param baseUrl 基础URL
     * @param apiKey  API密钥
     * @return API工厂实例
     */
    public static DifyApiFactory newInstance(String baseUrl, String apiKey) {
        return new DifyApiFactory(baseUrl, apiKey);
    }

    /**
     * 创建基础API客户端
     *
     * @return 基础API客户端
     */
    public DifyBaseApi newDifyBaseApi() {
        return new DifyBaseApi(baseUrl, apiKey);
    }

    /**
     * 创建聊天API客户端
     *
     * @return 聊天API客户端
     */
    public DifyChatApi newDifyChatApi() {
        return new DifyChatApi(baseUrl, apiKey);
    }

    /**
     * 创建聊天流API客户端
     *
     * @return 聊天流API客户端
     */
    public DifyChatFlowApi newDifyChatFlowApi() {
        return new DifyChatFlowApi(baseUrl, apiKey);
    }

    /**
     * 创建完成API客户端
     *
     * @return 完成API客户端
     */
    public DifyCompletionApi newDifyCompletionApi() {
        return new DifyCompletionApi(baseUrl, apiKey);
    }

    /**
     * 创建数据集API客户端
     *
     * @return 数据集API客户端
     */
    public DifyDatasetsApi newDifyDatasetsApi() {
        return new DifyDatasetsApi(baseUrl, apiKey);
    }

    /**
     * 创建工作流API客户端
     *
     * @return 工作流API客户端
     */
    public DifyWorkflowApi newDifyWorkflowApi() {
        return new DifyWorkflowApi(baseUrl, apiKey);
    }
}
