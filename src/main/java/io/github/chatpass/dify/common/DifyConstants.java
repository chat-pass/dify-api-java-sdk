package io.github.chatpass.dify.common;

/**
 * Dify SDK 常量定义
 *
 * @author chat-pass
 */
public final class DifyConstants {

    // 流式响应相关常量
    public static final String STREAM_DONE_MARKER = "[DONE]";
    public static final String STREAM_DATA_PREFIX = "data:";

    // 媒体类型常量
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final String APPLICATION_JSON = "application/json";
    public static final String TEXT_EVENT_STREAM = "text/event-stream";

    // 超时设置常量
    public static final int DEFAULT_CALL_TIMEOUT_SECONDS = 120;
    public static final int DEFAULT_READ_TIMEOUT_SECONDS = 120;
    public static final int DEFAULT_PING_INTERVAL_SECONDS = 20;

    // 重试相关常量
    public static final int DEFAULT_MAX_RETRIES = 3;
    public static final long DEFAULT_RETRY_DELAY_MS = 1000;

    // 错误信息常量
    public static final String ERROR_NULL_MESSAGE = "Request cannot be null";
    public static final String ERROR_NULL_USER = "User cannot be null";
    public static final String ERROR_NULL_QUERY = "Query cannot be null";
    public static final String ERROR_NULL_FILE = "File cannot be null";
    public static final String ERROR_FILE_NOT_EXISTS = "File does not exist: %s";
    public static final String ERROR_FILE_NOT_READABLE = "Cannot read file: %s";
    public static final String ERROR_UNKNOWN_API = "Unknown API error";
    public static final String ERROR_INVALID_FILE_TYPE = "Invalid file type";
    public static final String ERROR_INVALID_TRANSFER_METHOD = "Invalid file transfer method";
    public static final String ERROR_MISSING_UPLOAD_FILE_ID = "Upload file ID is required for local file transfer";
    public static final String ERROR_MISSING_FILE_URL = "File URL is required for remote URL transfer";
    public static final String ERROR_EMPTY_INPUTS_KEY = "Input parameter key cannot be empty";

    // 日志格式模板
    public static final String LOG_SEND_CHAT_MESSAGE = "send chat message: {}";
    public static final String LOG_STREAM_CHAT_MESSAGE = "send stream chat message: user={}, inputs={}";
    public static final String LOG_STOP_CHAT_MESSAGE = "stop chat message taskId {} request: {}";
    public static final String LOG_GET_SUGGESTED_QUESTIONS = "get suggested questions messageId={}, user={}";
    public static final String LOG_GET_CONVERSATIONS = "get conversations list user={}, lastId={}, limit={}, sortBy={}";
    public static final String LOG_GET_MESSAGES = "get message list: conversationId={}, user={}, firstId={}, limit={}";
    public static final String LOG_DELETE_CONVERSATION = "delete conversation: conversationId={}, user={}";
    public static final String LOG_MESSAGE_FEEDBACK = "message feedback: messageId={}, rating={}, user={}";
    public static final String LOG_AUDIO_TO_TEXT = "audioToText: fileName={}, user={}";

    private DifyConstants() {
        // 防止实例化
        throw new AssertionError("Constants class should not be instantiated");
    }
}
