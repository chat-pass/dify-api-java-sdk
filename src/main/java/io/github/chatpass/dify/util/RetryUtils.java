package io.github.chatpass.dify.util;

import io.github.chatpass.dify.common.DifyConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

/**
 * 重试工具类
 *
 * @author chat-pass
 */
@Slf4j
public final class RetryUtils {

    private RetryUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * 执行带重试的操作
     *
     * @param callable       要执行的操作
     * @param maxRetries     最大重试次数
     * @param retryDelayMs   重试间隔毫秒数
     * @param retryCondition 重试条件判断
     * @param <T>            返回类型
     * @return 执行结果
     * @throws Exception 如果所有重试都失败
     */
    public static <T> T executeWithRetry(
            Callable<T> callable,
            int maxRetries,
            long retryDelayMs,
            Predicate<Exception> retryCondition) throws Exception {

        ValidationUtils.validateNonNull(callable, "callable");
        ValidationUtils.validateNonNegative(maxRetries, "maxRetries");
        ValidationUtils.validateNonNegative((int) retryDelayMs, "retryDelayMs");

        Exception lastException = null;

        for (int attempt = 0; attempt <= maxRetries; attempt++) {
            try {
                return callable.call();
            } catch (Exception e) {
                lastException = e;

                if (attempt == maxRetries) {
                    log.error("All {} retry attempts failed", maxRetries + 1, e);
                    break;
                }

                if (retryCondition != null && !retryCondition.test(e)) {
                    log.debug("Exception not retryable, failing immediately", e);
                    break;
                }

                log.warn("Attempt {} failed, retrying in {}ms", attempt + 1, retryDelayMs, e);

                if (retryDelayMs > 0) {
                    try {
                        Thread.sleep(retryDelayMs);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Retry interrupted", ie);
                    }
                }
            }
        }

        throw lastException;
    }

    /**
     * 使用默认配置执行带重试的操作
     *
     * @param callable 要执行的操作
     * @param <T>      返回类型
     * @return 执行结果
     * @throws Exception 如果所有重试都失败
     */
    public static <T> T executeWithDefaultRetry(Callable<T> callable) throws Exception {
        return executeWithRetry(
                callable,
                DifyConstants.DEFAULT_MAX_RETRIES,
                DifyConstants.DEFAULT_RETRY_DELAY_MS,
                RetryUtils::isRetryableException);
    }

    /**
     * 判断异常是否可重试
     *
     * @param exception 异常
     * @return 是否可重试
     */
    private static boolean isRetryableException(Exception exception) {
        // 网络相关异常通常可以重试
        return exception instanceof java.io.IOException ||
                exception instanceof java.net.SocketTimeoutException ||
                exception instanceof java.net.ConnectException ||
                (exception.getMessage() != null &&
                        exception.getMessage().toLowerCase().contains("timeout"));
    }
}
