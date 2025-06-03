package io.github.chatpass.dify.api;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.github.chatpass.dify.api.callback.ChatflowStreamCallback;
import io.github.chatpass.dify.api.callback.StreamEventDispatcher;
import io.github.chatpass.dify.data.enums.AnnotationReplyAction;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.request.*;
import io.github.chatpass.dify.data.response.*;
import io.github.chatpass.dify.data.request.*;
import io.github.chatpass.dify.service.DifyChatFlowApiService;

import io.github.chatpass.dify.DIfyApiServiceGenerator;
import io.github.chatpass.dify.data.response.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

@Slf4j
public class DifyChatFlowApi {

    private final DifyChatFlowApiService chatFlowApiService;

    public DifyChatFlowApi(String baseUrl, String apiKey) {
        this.chatFlowApiService = DIfyApiServiceGenerator.createService(DifyChatFlowApiService.class, apiKey, baseUrl);
    }


    public ChatMessageResponse sendChatMessage(ChatMessageRequest message){
        log.debug("send chat message: {}", message);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.sendChatMessage(message));
    }

    public void sendChatMessageStream(ChatMessageRequest message, ChatflowStreamCallback callback){
        log.debug("send stream chat message: user={}, inputs={}", message.getUser(), message.getInputs() != null ? message.getInputs().keySet() : null);
        message.setResponseMode(ResponseMode.STREAMING);
        Call<ResponseBody> call = chatFlowApiService.sendChatMessageStream(message);
        DIfyApiServiceGenerator.executeStreamRequest(call,(line) -> DIfyApiServiceGenerator.processStreamLine(line, callback, (data, eventType) -> {
            StreamEventDispatcher.dispatchChatFlowEvent(callback, data, eventType); 
        }), callback::onException);
    }

    public StopChatMessageResponse stopChatMessage(String taskId, StopChatMessageRequest request){
        log.debug("stop chat message taskId {} request: {}", taskId,request);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.stopChatMessage(taskId,request));
    }

    public SuggestedQuestionsResponse getSuggestedQuestions(String messageId, String user){
        log.debug("get suggested questions messageId={}, user={}", messageId,user);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.getSuggestedQuestions(messageId,user));
    }

    public ConversationListResponse getConversations(String user, String lastId, Integer limit, String sortBy){
        log.debug("get Conversations list user={}, lastId={}, limit={}, sortBy={}", user, lastId, limit, sortBy);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.getConversations(user, lastId, limit, sortBy));
    }

    public ChatMessageListResponse getMessages(String conversationId, String user, String firstId, Integer limit){
        log.debug("get message list : conversationId={}, user={}, firstId={}, limit={}", conversationId, user, firstId, limit);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.getMessages(conversationId, user, firstId, limit));
    }

    public LittleResponse deleteConversation(String conversationId, SimpleUserRequest user){
        log.debug("delete Conversation: conversationId={}, user={}", conversationId, user.getUser());
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.deleteConversation(conversationId,user));
    }

    public Conversation renameConversation(String conversationId, RenameConversationRequest request){
        log.debug("rename Conversation: conversationId={}, request={}", conversationId, request);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.renameConversation(conversationId,request));
    }

    public ConversationVariablesResponse getConversationVariables(String conversationId, String user, String lastId, Integer limit,String variableName){
        log.debug("get Conversation Variables list : conversationId={}, user={}, lastId={}, limit={}.variableName={}", conversationId, user, lastId, limit,variableName);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.getConversationVariables(conversationId,user,lastId,limit,variableName));
    }

    public LittleResponse feedbackMessage(String messageId, FeedBackRequest request){
        log.debug("message feedback: messageId={}, rating={}, user={}", messageId, request.getRating(), request.getUser());
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.feedbackMessage(messageId, request));
    }

    public FeedBackListResponse getFeedBackList(Integer page,Integer limit){
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.getAppfeedbacks(page, limit));
    }

    public byte[] textToAudio(TextToAudioRequest request) throws IOException {
        ResponseBody responseBody = DIfyApiServiceGenerator.executeSync(chatFlowApiService.textToAudio(request));
        return responseBody.bytes();
    }

    public AudioToTextResponse audioToText(File file, String user) throws IOException{
        log.debug("audioToText: fileName={}, user={}", file.getName(), user);
        Path filePath = file.toPath();
        String mimeType = Files.probeContentType(filePath);
        RequestBody requestFile = RequestBody.create(file,MediaType.parse(mimeType));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        MultipartBody.Part userPart = MultipartBody.Part.createFormData("user", user);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.audioToText(filePart, userPart));
    }

    public Annotation createAnnotation(AnnotationRequest request){
        log.debug("create annotation request: {}", request);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.createAnnotation(request));
    }

    public AnnotationListResponse getAnnotationList(Integer page,Integer limit){
        log.debug("get annotation list page {} limit: {}", page,limit);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.getAnnotationList(page,limit));
    }

    public void deleteAnnotation(String id){
        log.debug("delete annotation id: {}", id);
        DIfyApiServiceGenerator.executeSync(chatFlowApiService.deleteAnnotation(id));
    }

    public Annotation updateAnnotation(String id,AnnotationRequest request){
        log.debug("update annotation id: {} request: {}", id,request);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.updateAnnotation(id,request));
    }

    public AnnotationReplyResponse annotationReply(AnnotationReplyAction action, AnnotationReplyRequest request){
        log.debug("annotation reply action: {} request: {}", action,request);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.annotationReply(action,request));
    }

    public AnnotationReplyResponse getAnnotationReplyStatus(AnnotationReplyAction action,String jobId){
        log.debug("get annotation reply status action: {} jobId: {}", action,jobId);
        return DIfyApiServiceGenerator.executeSync(chatFlowApiService.getAnnotationReplyStatus(action,jobId));
    }
}
