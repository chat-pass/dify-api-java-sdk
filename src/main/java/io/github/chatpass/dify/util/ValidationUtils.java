package io.github.chatpass.dify.util;

import io.github.chatpass.dify.common.DifyConstants;

import java.io.File;
import java.util.Objects;

/**
 * 验证工具类
 *
 * @author chat-pass
 */
public final class ValidationUtils {

    private ValidationUtils() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * 验证文件存在且可读
     *
     * @param file      文件对象
     * @param paramName 参数名称
     * @return 验证后的文件对象
     * @throws IllegalArgumentException 如果文件为null、不存在或不可读
     */
    public static File validateFile(File file, String paramName) {
        Objects.requireNonNull(file, paramName + " cannot be null");

        if (!file.exists()) {
            throw new IllegalArgumentException(
                    String.format(DifyConstants.ERROR_FILE_NOT_EXISTS, file.getPath()));
        }

        if (!file.canRead()) {
            throw new IllegalArgumentException(
                    String.format(DifyConstants.ERROR_FILE_NOT_READABLE, file.getPath()));
        }

        return file;
    }

    /**
     * 验证字符串非空且非空白
     *
     * @param value     字符串值
     * @param paramName 参数名称
     * @return 验证后的字符串值
     * @throws IllegalArgumentException 如果字符串为null或空白
     */
    public static String validateNonBlank(String value, String paramName) {
        Objects.requireNonNull(value, paramName + " cannot be null");
        if (value.trim().isEmpty()) {
            throw new IllegalArgumentException(paramName + " cannot be blank");
        }
        return value;
    }

    /**
     * 验证数值为正数
     *
     * @param value     数值
     * @param paramName 参数名称
     * @return 验证后的数值
     * @throws IllegalArgumentException 如果数值小于等于0
     */
    public static int validatePositive(int value, String paramName) {
        if (value <= 0) {
            throw new IllegalArgumentException(paramName + " must be positive, but was: " + value);
        }
        return value;
    }

    /**
     * 验证数值为非负数
     *
     * @param value     数值
     * @param paramName 参数名称
     * @return 验证后的数值
     * @throws IllegalArgumentException 如果数值小于0
     */
    public static int validateNonNegative(int value, String paramName) {
        if (value < 0) {
            throw new IllegalArgumentException(paramName + " must be non-negative, but was: " + value);
        }
        return value;
    }

    /**
     * 验证对象非空
     *
     * @param value     对象值
     * @param paramName 参数名称
     * @param <T>       对象类型
     * @return 验证后的对象值
     * @throws IllegalArgumentException 如果对象为null
     */
    public static <T> T validateNonNull(T value, String paramName) {
        return Objects.requireNonNull(value, paramName + " cannot be null");
    }
}
