package io.github.chatpass.dify.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

/**
 * JSON工具类（线程安全）
 *
 * @author chat-pass
 */
@Slf4j
public final class JSONUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JSONUtils() {
        // 防止实例化
        throw new AssertionError("Utility class should not be instantiated");
    }

    static {
        // 配置ObjectMapper
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }

    /**
     * 对象转JSON字符串
     *
     * @param obj 对象
     * @return JSON字符串
     * @throws IllegalArgumentException 如果对象为null
     */
    public static String toJson(Object obj) {
        Objects.requireNonNull(obj, "Object cannot be null");

        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert object to JSON: {}", obj.getClass().getSimpleName(), e);
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    /**
     * 安全的对象转JSON字符串（不抛异常）
     *
     * @param obj 对象
     * @return JSON字符串，失败时返回null
     */
    public static String toJsonSafely(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return toJson(obj);
        } catch (Exception e) {
            log.debug("Failed to convert object to JSON safely: {}", obj.getClass().getSimpleName(), e);
            return null;
        }
    }

    /**
     * 检查字符串是否为有效的JSON
     *
     * @param jsonString JSON字符串
     * @return 是否为有效JSON
     */
    public static boolean isValidJson(String jsonString) {
        if (jsonString == null || jsonString.trim().isEmpty()) {
            return false;
        }
        try {
            OBJECT_MAPPER.readTree(jsonString);
            return true;
        } catch (JsonProcessingException e) {
            log.debug("Invalid JSON format: {}", jsonString, e);
            return false;
        }
    }

    /**
     * JSON字符串转对象
     *
     * @param json  JSON字符串
     * @param clazz 目标类型
     * @param <T>   泛型类型
     * @return 转换后的对象
     * @throws IllegalArgumentException 如果参数为null
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        ValidationUtils.validateNonBlank(json, "json");
        Objects.requireNonNull(clazz, "Target class cannot be null");

        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            log.error("Failed to parse JSON to {}: {}", clazz.getSimpleName(), json, e);
            throw new RuntimeException("Failed to parse JSON: " + e.getMessage(), e);
        }
    }

    /**
     * 安全的JSON解析方法，失败时返回null而不抛异常
     *
     * @param json  JSON字符串
     * @param clazz 目标类型
     * @param <T>   泛型类型
     * @return 转换后的对象，失败时返回null
     */
    public static <T> T fromJsonSafely(String json, Class<T> clazz) {
        if (json == null || json.trim().isEmpty() || clazz == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            log.debug("Failed to parse JSON to {} safely: {}", clazz.getSimpleName(), json, e);
            return null;
        }
    }

    /**
     * 格式化JSON字符串（美化输出）
     *
     * @param json JSON字符串
     * @return 格式化后的JSON字符串
     */
    public static String prettyFormat(String json) {
        ValidationUtils.validateNonBlank(json, "json");

        try {
            Object obj = OBJECT_MAPPER.readValue(json, Object.class);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            log.error("Failed to format JSON: {}", json, e);
            throw new RuntimeException("Failed to format JSON", e);
        }
    }
}
