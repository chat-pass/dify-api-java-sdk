package io.github.chatpass.dify.api;

import io.github.chatpass.dify.api.callback.ChatStreamCallback;
import io.github.chatpass.dify.api.callback.StreamEventDispatcher;
import io.github.chatpass.dify.common.DifyConstants;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.request.*;
import io.github.chatpass.dify.data.response.*;
import io.github.chatpass.dify.service.DifyChatApiService;
import io.github.chatpass.dify.util.ValidationUtils;
import io.github.chatpass.dify.DIfyApiServiceGenerator;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Dify聊天API客户端
 *
 * @author chat-pass
 */
@Slf4j
public class DifyChatApi extends BaseApi {

    private final DifyChatApiService chatApiService;

    public DifyChatApi(String baseUrl, String apiKey) {
        super(baseUrl, apiKey);
        this.chatApiService = createService(DifyChatApiService.class);
    }

    public ChatMessageResponse sendChatMessage(ChatMessageRequest message) {
        // 基础参数校验
        ValidationUtils.validateNonNull(message, "message");
        ValidationUtils.validateNonBlank(message.getUser(), "user");
        ValidationUtils.validateNonBlank(message.getQuery(), "query");

        // 可选参数校验
        validateChatMessageRequest(message);

        log.debug(DifyConstants.LOG_SEND_CHAT_MESSAGE, message);
        return DIfyApiServiceGenerator.executeSync(chatApiService.sendChatMessage(message));
    }

    /**
     * 校验聊天消息请求的可选参数
     *
     * @param message 聊天消息请求
     */
    private void validateChatMessageRequest(ChatMessageRequest message) {
        // 校验文件列表（如果存在）
        if (message.getFiles() != null) {
            message.getFiles().forEach(file -> {
                if (file != null) {
                    ValidationUtils.validateNonNull(file.getType(), "file.type");
                    ValidationUtils.validateNonNull(file.getTransferMethod(), "file.transferMethod");

                    // 根据传输方式校验相应的字段
                    if (file.getTransferMethod() != null) {
                        switch (file.getTransferMethod()) {
                            case LOCAL_FILE:
                                ValidationUtils.validateNonBlank(file.getUploadFileId(), "file.uploadFileId");
                                break;
                            case REMOTE_URL:
                                ValidationUtils.validateNonBlank(file.getUrl(), "file.url");
                                break;
                        }
                    }
                }
            });
        }

        // 校验输入参数的键值（如果存在）
        if (message.getInputs() != null) {
            message.getInputs().keySet().forEach(key -> {
                ValidationUtils.validateNonBlank(key, "inputs key");
            });
        }

        // conversationId 是可选的，不需要校验
        // responseMode 由枚举保证类型安全，不需要额外校验
        // autoGenerateName 有默认值，不需要校验
    }

    public void sendChatMessageStream(ChatMessageRequest message, ChatStreamCallback callback) {
        // 基础参数校验
        ValidationUtils.validateNonNull(message, "message");
        ValidationUtils.validateNonNull(callback, "callback");
        ValidationUtils.validateNonBlank(message.getUser(), "user");
        ValidationUtils.validateNonBlank(message.getQuery(), "query");

        // 可选参数校验
        validateChatMessageRequest(message);

        log.debug(DifyConstants.LOG_STREAM_CHAT_MESSAGE, message.getUser(),
                message.getInputs() != null ? message.getInputs().keySet() : null);
        message.setResponseMode(ResponseMode.STREAMING);
        Call<ResponseBody> call = chatApiService.sendChatMessageStream(message);
        DIfyApiServiceGenerator.executeStreamRequest(call,
                (line) -> DIfyApiServiceGenerator.processStreamLine(line, callback, (data, eventType) -> {
                    StreamEventDispatcher.dispatchChatEvent(callback, data, eventType);
                }), callback::onException);
    }

    public StopChatMessageResponse stopChatMessage(String taskId, StopChatMessageRequest request) {
        ValidationUtils.validateNonBlank(taskId, "taskId");
        ValidationUtils.validateNonNull(request, "request");

        log.debug(DifyConstants.LOG_STOP_CHAT_MESSAGE, taskId, request);
        return DIfyApiServiceGenerator.executeSync(chatApiService.stopChatMessage(taskId, request));
    }

    public SuggestedQuestionsResponse getSuggestedQuestions(String messageId, String user) {
        ValidationUtils.validateNonBlank(messageId, "messageId");
        ValidationUtils.validateNonBlank(user, "user");

        log.debug(DifyConstants.LOG_GET_SUGGESTED_QUESTIONS, messageId, user);
        return DIfyApiServiceGenerator.executeSync(chatApiService.getSuggestedQuestions(messageId, user));
    }

    public ConversationListResponse getConversations(String user, String lastId, Integer limit, String sortBy) {
        ValidationUtils.validateNonBlank(user, "user");
        if (limit != null) {
            ValidationUtils.validatePositive(limit, "limit");
        }

        log.debug(DifyConstants.LOG_GET_CONVERSATIONS, user, lastId, limit, sortBy);
        return DIfyApiServiceGenerator.executeSync(chatApiService.getConversations(user, lastId, limit, sortBy));
    }

    public ChatMessageListResponse getMessages(String conversationId, String user, String firstId, Integer limit) {
        ValidationUtils.validateNonBlank(conversationId, "conversationId");
        ValidationUtils.validateNonBlank(user, "user");
        if (limit != null) {
            ValidationUtils.validatePositive(limit, "limit");
        }

        log.debug(DifyConstants.LOG_GET_MESSAGES, conversationId, user, firstId, limit);
        return DIfyApiServiceGenerator.executeSync(chatApiService.getMessages(conversationId, user, firstId, limit));
    }

    public LittleResponse deleteConversation(String conversationId, SimpleUserRequest user) {
        ValidationUtils.validateNonBlank(conversationId, "conversationId");
        ValidationUtils.validateNonNull(user, "user");
        ValidationUtils.validateNonBlank(user.getUser(), "user.user");

        log.debug(DifyConstants.LOG_DELETE_CONVERSATION, conversationId, user.getUser());
        return DIfyApiServiceGenerator.executeSync(chatApiService.deleteConversation(conversationId, user));
    }

    public ConversationVariablesResponse getConversationVariables(String conversationId, String user, String lastId,
            Integer limit, String variableName) {
        ValidationUtils.validateNonBlank(conversationId, "conversationId");
        ValidationUtils.validateNonBlank(user, "user");
        if (limit != null) {
            ValidationUtils.validatePositive(limit, "limit");
        }

        log.debug("get Conversation Variables list : conversationId={}, user={}, lastId={}, limit={}.variableName={}",
                conversationId, user, lastId, limit, variableName);
        return DIfyApiServiceGenerator.executeSync(
                chatApiService.getConversationVariables(conversationId, user, lastId, limit, variableName));
    }

    public LittleResponse feedbackMessage(String messageId, FeedBackRequest request) {
        ValidationUtils.validateNonBlank(messageId, "messageId");
        ValidationUtils.validateNonNull(request, "request");
        ValidationUtils.validateNonBlank(request.getUser(), "user");

        log.debug(DifyConstants.LOG_MESSAGE_FEEDBACK, messageId, request.getRating(), request.getUser());
        return DIfyApiServiceGenerator.executeSync(chatApiService.feedbackMessage(messageId, request));
    }

    public FeedBackListResponse getFeedBackList(Integer page, Integer limit) {
        if (page != null) {
            ValidationUtils.validateNonNegative(page, "page");
        }
        if (limit != null) {
            ValidationUtils.validatePositive(limit, "limit");
        }

        return DIfyApiServiceGenerator.executeSync(chatApiService.getAppfeedbacks(page, limit));
    }

    public byte[] textToAudio(TextToAudioRequest request) throws IOException {
        ValidationUtils.validateNonNull(request, "request");
        ValidationUtils.validateNonBlank(request.getText(), "text");
        ValidationUtils.validateNonBlank(request.getUser(), "user");

        ResponseBody responseBody = DIfyApiServiceGenerator.executeSync(chatApiService.textToAudio(request));
        return responseBody.bytes();
    }

    public AudioToTextResponse audioToText(File file, String user) throws IOException {
        ValidationUtils.validateFile(file, "file");
        ValidationUtils.validateNonBlank(user, "user");

        log.debug(DifyConstants.LOG_AUDIO_TO_TEXT, file.getName(), user);
        Path filePath = file.toPath();
        String mimeType = Files.probeContentType(filePath);
        RequestBody requestFile = RequestBody.create(file,
                MediaType.parse(mimeType != null ? mimeType : DifyConstants.APPLICATION_JSON));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        MultipartBody.Part userPart = MultipartBody.Part.createFormData("user", user);
        return DIfyApiServiceGenerator.executeSync(chatApiService.audioToText(filePart, userPart));
    }
}
