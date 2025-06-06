package io.github.chatpass.dify.api;

import io.github.chatpass.dify.DIfyApiServiceGenerator;
import io.github.chatpass.dify.config.DifyApiConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * API基类，提供公共功能
 *
 * @author chat-pass
 */
@Slf4j
public abstract class BaseApi {

    protected final String baseUrl;
    protected final String apiKey;
    protected final DifyApiConfig config;

    protected BaseApi(String baseUrl, String apiKey) {
        this.baseUrl = Objects.requireNonNull(baseUrl, "Base URL cannot be null");
        this.apiKey = Objects.requireNonNull(apiKey, "API key cannot be null");
        this.config = null;
        log.debug("Initializing API client for baseUrl: {}", baseUrl);
    }

    protected BaseApi(DifyApiConfig config) {
        this.baseUrl = config.getBaseUrl();
        this.apiKey = config.getApiKey();
        this.config = config;
        log.debug("Initializing API client with config: {}", config);
    }

    /**
     * 创建服务实例
     *
     * @param serviceClass 服务类
     * @param <T>          服务类型
     * @return 服务实例
     */
    protected <T> T createService(Class<T> serviceClass) {
        return DIfyApiServiceGenerator.createService(serviceClass, apiKey, baseUrl);
    }
}
