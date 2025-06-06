package io.github.chatpass.dify.config;

import io.github.chatpass.dify.common.DifyConstants;
import io.github.chatpass.dify.util.ValidationUtils;

/**
 * Dify API配置类
 *
 * @author chat-pass
 */
public final class DifyApiConfig {

    private final String baseUrl;
    private final String apiKey;
    private final int callTimeoutSeconds;
    private final int readTimeoutSeconds;
    private final int pingIntervalSeconds;
    private final int maxRetries;
    private final long retryDelayMs;
    private final boolean enableRetry;
    private final boolean enableLogging;

    private DifyApiConfig(Builder builder) {
        this.baseUrl = ValidationUtils.validateNonBlank(builder.baseUrl, "baseUrl");
        this.apiKey = ValidationUtils.validateNonBlank(builder.apiKey, "apiKey");
        this.callTimeoutSeconds = ValidationUtils.validatePositive(builder.callTimeoutSeconds, "callTimeoutSeconds");
        this.readTimeoutSeconds = ValidationUtils.validatePositive(builder.readTimeoutSeconds, "readTimeoutSeconds");
        this.pingIntervalSeconds = ValidationUtils.validatePositive(builder.pingIntervalSeconds, "pingIntervalSeconds");
        this.maxRetries = ValidationUtils.validateNonNegative(builder.maxRetries, "maxRetries");
        this.retryDelayMs = ValidationUtils.validateNonNegative((int) builder.retryDelayMs, "retryDelayMs");
        this.enableRetry = builder.enableRetry;
        this.enableLogging = builder.enableLogging;
    }

    // Getters
    public String getBaseUrl() {
        return baseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public int getCallTimeoutSeconds() {
        return callTimeoutSeconds;
    }

    public int getReadTimeoutSeconds() {
        return readTimeoutSeconds;
    }

    public int getPingIntervalSeconds() {
        return pingIntervalSeconds;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public long getRetryDelayMs() {
        return retryDelayMs;
    }

    public boolean isEnableRetry() {
        return enableRetry;
    }

    public boolean isEnableLogging() {
        return enableLogging;
    }

    /**
     * 创建Builder实例
     *
     * @param baseUrl 基础URL
     * @param apiKey  API密钥
     * @return Builder实例
     */
    public static Builder builder(String baseUrl, String apiKey) {
        return new Builder(baseUrl, apiKey);
    }

    /**
     * 配置Builder类
     */
    public static final class Builder {
        private final String baseUrl;
        private final String apiKey;
        private int callTimeoutSeconds = DifyConstants.DEFAULT_CALL_TIMEOUT_SECONDS;
        private int readTimeoutSeconds = DifyConstants.DEFAULT_READ_TIMEOUT_SECONDS;
        private int pingIntervalSeconds = DifyConstants.DEFAULT_PING_INTERVAL_SECONDS;
        private int maxRetries = DifyConstants.DEFAULT_MAX_RETRIES;
        private long retryDelayMs = DifyConstants.DEFAULT_RETRY_DELAY_MS;
        private boolean enableRetry = true;
        private boolean enableLogging = true;

        private Builder(String baseUrl, String apiKey) {
            this.baseUrl = baseUrl;
            this.apiKey = apiKey;
        }

        /**
         * 设置调用超时时间
         *
         * @param callTimeoutSeconds 超时秒数
         * @return Builder实例
         */
        public Builder callTimeout(int callTimeoutSeconds) {
            this.callTimeoutSeconds = callTimeoutSeconds;
            return this;
        }

        /**
         * 设置读取超时时间
         *
         * @param readTimeoutSeconds 超时秒数
         * @return Builder实例
         */
        public Builder readTimeout(int readTimeoutSeconds) {
            this.readTimeoutSeconds = readTimeoutSeconds;
            return this;
        }

        /**
         * 设置ping间隔时间
         *
         * @param pingIntervalSeconds 间隔秒数
         * @return Builder实例
         */
        public Builder pingInterval(int pingIntervalSeconds) {
            this.pingIntervalSeconds = pingIntervalSeconds;
            return this;
        }

        /**
         * 设置重试配置
         *
         * @param maxRetries   最大重试次数
         * @param retryDelayMs 重试延迟毫秒数
         * @return Builder实例
         */
        public Builder retryConfig(int maxRetries, long retryDelayMs) {
            this.maxRetries = maxRetries;
            this.retryDelayMs = retryDelayMs;
            return this;
        }

        /**
         * 设置是否启用重试
         *
         * @param enableRetry 是否启用重试
         * @return Builder实例
         */
        public Builder enableRetry(boolean enableRetry) {
            this.enableRetry = enableRetry;
            return this;
        }

        /**
         * 设置是否启用日志
         *
         * @param enableLogging 是否启用日志
         * @return Builder实例
         */
        public Builder enableLogging(boolean enableLogging) {
            this.enableLogging = enableLogging;
            return this;
        }

        /**
         * 构建配置对象
         *
         * @return 配置对象
         */
        public DifyApiConfig build() {
            return new DifyApiConfig(this);
        }
    }

    @Override
    public String toString() {
        return "DifyApiConfig{" +
                "baseUrl='" + baseUrl + '\'' +
                ", callTimeoutSeconds=" + callTimeoutSeconds +
                ", readTimeoutSeconds=" + readTimeoutSeconds +
                ", pingIntervalSeconds=" + pingIntervalSeconds +
                ", maxRetries=" + maxRetries +
                ", retryDelayMs=" + retryDelayMs +
                ", enableRetry=" + enableRetry +
                ", enableLogging=" + enableLogging +
                '}';
    }
}
