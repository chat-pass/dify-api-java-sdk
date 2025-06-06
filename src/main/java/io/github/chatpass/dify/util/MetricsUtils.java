package io.github.chatpass.dify.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 性能监控工具类
 *
 * @author chat-pass
 */
@Slf4j
public final class MetricsUtils {

    // 请求计数器
    private static final ConcurrentHashMap<String, LongAdder> REQUEST_COUNTERS = new ConcurrentHashMap<>();

    // 错误计数器
    private static final ConcurrentHashMap<String, LongAdder> ERROR_COUNTERS = new ConcurrentHashMap<>();

    // 响应时间统计
    private static final ConcurrentHashMap<String, ResponseTimeStats> RESPONSE_TIME_STATS = new ConcurrentHashMap<>();

    private MetricsUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * 增加请求计数
     *
     * @param apiName API名称
     */
    public static void incrementRequestCount(String apiName) {
        REQUEST_COUNTERS.computeIfAbsent(apiName, k -> new LongAdder()).increment();
    }

    /**
     * 增加错误计数
     *
     * @param apiName API名称
     */
    public static void incrementErrorCount(String apiName) {
        ERROR_COUNTERS.computeIfAbsent(apiName, k -> new LongAdder()).increment();
    }

    /**
     * 记录响应时间
     *
     * @param apiName        API名称
     * @param responseTimeMs 响应时间（毫秒）
     */
    public static void recordResponseTime(String apiName, long responseTimeMs) {
        RESPONSE_TIME_STATS.computeIfAbsent(apiName, k -> new ResponseTimeStats())
                .record(responseTimeMs);
    }

    /**
     * 获取请求计数
     *
     * @param apiName API名称
     * @return 请求计数
     */
    public static long getRequestCount(String apiName) {
        LongAdder counter = REQUEST_COUNTERS.get(apiName);
        return counter != null ? counter.sum() : 0;
    }

    /**
     * 获取错误计数
     *
     * @param apiName API名称
     * @return 错误计数
     */
    public static long getErrorCount(String apiName) {
        LongAdder counter = ERROR_COUNTERS.get(apiName);
        return counter != null ? counter.sum() : 0;
    }

    /**
     * 获取平均响应时间
     *
     * @param apiName API名称
     * @return 平均响应时间（毫秒）
     */
    public static double getAverageResponseTime(String apiName) {
        ResponseTimeStats stats = RESPONSE_TIME_STATS.get(apiName);
        return stats != null ? stats.getAverage() : 0.0;
    }

    /**
     * 获取最大响应时间
     *
     * @param apiName API名称
     * @return 最大响应时间（毫秒）
     */
    public static long getMaxResponseTime(String apiName) {
        ResponseTimeStats stats = RESPONSE_TIME_STATS.get(apiName);
        return stats != null ? stats.getMax() : 0;
    }

    /**
     * 获取错误率
     *
     * @param apiName API名称
     * @return 错误率（0-1之间）
     */
    public static double getErrorRate(String apiName) {
        long requestCount = getRequestCount(apiName);
        if (requestCount == 0) {
            return 0.0;
        }
        long errorCount = getErrorCount(apiName);
        return (double) errorCount / requestCount;
    }

    /**
     * 打印所有API的性能统计
     */
    public static void printMetrics() {
        log.info("=== API Performance Metrics ===");

        REQUEST_COUNTERS.keySet().forEach(apiName -> {
            long requests = getRequestCount(apiName);
            long errors = getErrorCount(apiName);
            double avgResponseTime = getAverageResponseTime(apiName);
            long maxResponseTime = getMaxResponseTime(apiName);
            double errorRate = getErrorRate(apiName);

            log.info(
                    "API: {} | Requests: {} | Errors: {} | Avg Response: {:.2f}ms | Max Response: {}ms | Error Rate: {:.2f}%",
                    apiName, requests, errors, avgResponseTime, maxResponseTime, errorRate * 100);
        });
    }

    /**
     * 清空所有统计数据
     */
    public static void clearMetrics() {
        REQUEST_COUNTERS.clear();
        ERROR_COUNTERS.clear();
        RESPONSE_TIME_STATS.clear();
        log.info("All metrics cleared");
    }

    /**
     * 响应时间统计类
     */
    private static class ResponseTimeStats {
        private final AtomicLong totalTime = new AtomicLong(0);
        private final AtomicLong count = new AtomicLong(0);
        private volatile long max = 0;

        public void record(long responseTime) {
            totalTime.addAndGet(responseTime);
            count.incrementAndGet();

            // 更新最大值（线程安全）
            long currentMax;
            do {
                currentMax = max;
                if (responseTime <= currentMax) {
                    break;
                }
            } while (!compareAndSetMax(currentMax, responseTime));
        }

        private boolean compareAndSetMax(long expect, long update) {
            synchronized (this) {
                if (max == expect) {
                    max = update;
                    return true;
                }
                return false;
            }
        }

        public double getAverage() {
            long totalCount = count.get();
            return totalCount > 0 ? (double) totalTime.get() / totalCount : 0.0;
        }

        public long getMax() {
            return max;
        }
    }

    /**
     * API调用计时器
     */
    public static class Timer implements AutoCloseable {
        private final String apiName;
        private final long startTime;

        public Timer(String apiName) {
            this.apiName = apiName;
            this.startTime = System.currentTimeMillis();
            incrementRequestCount(apiName);
        }

        @Override
        public void close() {
            long responseTime = System.currentTimeMillis() - startTime;
            recordResponseTime(apiName, responseTime);
        }

        /**
         * 标记为错误
         */
        public void markError() {
            incrementErrorCount(apiName);
        }
    }
}
