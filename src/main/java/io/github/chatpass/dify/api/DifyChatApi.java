package io.github.chatpass.dify.api;

import io.github.chatpass.dify.api.callback.ChatStreamCallback;
import io.github.chatpass.dify.api.callback.StreamEventDispatcher;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.request.*;
import io.github.chatpass.dify.data.response.*;
import io.github.chatpass.dify.data.request.*;
import io.github.chatpass.dify.data.response.*;
import io.github.chatpass.dify.service.DifyChatApiService;
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

@Slf4j
public class DifyChatApi {

    private final String baseUrl;
    private final String apiKey;
    private final DifyChatApiService chatApiService;


    public DifyChatApi(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
        this.chatApiService = DIfyApiServiceGenerator.createService(DifyChatApiService.class,apiKey,baseUrl);;
    }

    public ChatMessageResponse sendChatMessage(ChatMessageRequest message){
        log.debug("send chat message: {}", message);
        return DIfyApiServiceGenerator.executeSync(chatApiService.sendChatMessage(message));
    }

    public void sendChatMessageStream(ChatMessageRequest message, ChatStreamCallback callback){
        log.debug("send stream chat message: user={}, inputs={}", message.getUser(), message.getInputs() != null ? message.getInputs().keySet() : null);
        message.setResponseMode(ResponseMode.STREAMING);
        Call<ResponseBody> call = chatApiService.sendChatMessageStream(message);
        DIfyApiServiceGenerator.executeStreamRequest(call,(line) -> DIfyApiServiceGenerator.processStreamLine(line, callback, (data, eventType) -> {
            StreamEventDispatcher.dispatchChatEvent(callback, data, eventType);
        }), callback::onException);
    }

    public StopChatMessageResponse stopChatMessage(String taskId, StopChatMessageRequest request){
        log.debug("stop chat message taskId {} request: {}", taskId,request);
        return DIfyApiServiceGenerator.executeSync(chatApiService.stopChatMessage(taskId,request));
    }

    public SuggestedQuestionsResponse getSuggestedQuestions(String messageId, String user){
        log.debug("get suggested questions messageId={}, user={}", messageId,user);
        return DIfyApiServiceGenerator.executeSync(chatApiService.getSuggestedQuestions(messageId,user));
    }

    public ConversationListResponse getConversations(String user, String lastId, Integer limit, String sortBy){
        log.debug("get Conversations list user={}, lastId={}, limit={}, sortBy={}", user, lastId, limit, sortBy);
        return DIfyApiServiceGenerator.executeSync(chatApiService.getConversations(user, lastId, limit, sortBy));
    }

    public ChatMessageListResponse getMessages(String conversationId, String user, String firstId, Integer limit){
        log.debug("get message list : conversationId={}, user={}, firstId={}, limit={}", conversationId, user, firstId, limit);
        return DIfyApiServiceGenerator.executeSync(chatApiService.getMessages(conversationId, user, firstId, limit));
    }

    public LittleResponse deleteConversation(String conversationId, SimpleUserRequest user){
        log.debug("delete Conversation: conversationId={}, user={}", conversationId, user.getUser());
        return DIfyApiServiceGenerator.executeSync(chatApiService.deleteConversation(conversationId,user));
    }

    public ConversationVariablesResponse getConversationVariables(String conversationId, String user, String lastId, Integer limit,String variableName){
        log.debug("get Conversation Variables list : conversationId={}, user={}, lastId={}, limit={}.variableName={}", conversationId, user, lastId, limit,variableName);
        return DIfyApiServiceGenerator.executeSync(chatApiService.getConversationVariables(conversationId,user,lastId,limit,variableName));
    }

    public LittleResponse feedbackMessage(String messageId, FeedBackRequest request){
        log.debug("message feedback: messageId={}, rating={}, user={}", messageId, request.getRating(), request.getUser());
        return DIfyApiServiceGenerator.executeSync(chatApiService.feedbackMessage(messageId, request));
    }

    public FeedBackListResponse getFeedBackList(Integer page,Integer limit){
        return DIfyApiServiceGenerator.executeSync(chatApiService.getAppfeedbacks(page, limit));
    }

    public byte[] textToAudio(TextToAudioRequest request) throws IOException {
        ResponseBody responseBody = DIfyApiServiceGenerator.executeSync(chatApiService.textToAudio(request));
        return responseBody.bytes();
    }

    public AudioToTextResponse audioToText(File file, String user) throws IOException{
        log.debug("audioToText: fileName={}, user={}", file.getName(), user);
        Path filePath = file.toPath();
        String mimeType = Files.probeContentType(filePath);
        RequestBody requestFile = RequestBody.create(file,MediaType.parse(mimeType));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        MultipartBody.Part userPart = MultipartBody.Part.createFormData("user", user);
        return DIfyApiServiceGenerator.executeSync(chatApiService.audioToText(filePart, userPart));
    }
}
