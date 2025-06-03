package io.github.chatpass.dify.api;

import io.github.chatpass.dify.service.DifyCompletionApiService;

import io.github.chatpass.dify.DIfyApiServiceGenerator;
import io.github.chatpass.dify.data.enums.AnnotationReplyAction;
import io.github.chatpass.dify.data.request.AnnotationReplyRequest;
import io.github.chatpass.dify.data.request.AnnotationRequest;
import io.github.chatpass.dify.data.request.CompletionMessageRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.io.IOException;

import io.github.chatpass.dify.api.callback.CompletionStreamCallback;
import io.github.chatpass.dify.api.callback.StreamEventDispatcher;
import io.github.chatpass.dify.data.request.FeedBackRequest;
import io.github.chatpass.dify.data.request.StopCompletionMessageRequest;
import io.github.chatpass.dify.data.request.TextToAudioRequest;
import io.github.chatpass.dify.data.response.Annotation;
import io.github.chatpass.dify.data.response.AnnotationListResponse;
import io.github.chatpass.dify.data.response.AnnotationReplyResponse;
import io.github.chatpass.dify.data.response.ChatCompletionResponse;
import io.github.chatpass.dify.data.response.FeedBackListResponse;
import io.github.chatpass.dify.data.response.LittleResponse;
import io.github.chatpass.dify.data.response.StopCompletionMessageResponse;

@Slf4j
public class DifyCompletionApi {

    private final DifyCompletionApiService completionApiService;


    public DifyCompletionApi(String baseUrl, String apiKey) {
        this.completionApiService = DIfyApiServiceGenerator.createService(DifyCompletionApiService.class,apiKey,baseUrl);;
    }

    public ChatCompletionResponse sendCompletionMessage(CompletionMessageRequest message){
        log.debug("send completion message: {}", message);
        return DIfyApiServiceGenerator.executeSync(completionApiService.sendCompletionMessage(message));
    }

    public void sendCompletionMessageStream(CompletionMessageRequest message, CompletionStreamCallback callback){
        log.debug("send completion message stream: {}", message);
        Call<ResponseBody> call = completionApiService.sendCompletionMessageStream(message);
        DIfyApiServiceGenerator.executeStreamRequest(call,(line) -> DIfyApiServiceGenerator.processStreamLine(line, callback, (data, eventType) -> {
            StreamEventDispatcher.dispatchCompletionEvent(callback, data);
        }), callback::onException);
    }


    public StopCompletionMessageResponse stopCompletionMessage(String taskId, StopCompletionMessageRequest request){
        log.debug("stop completion message taskId {} request: {}", taskId,request);
        return DIfyApiServiceGenerator.executeSync(completionApiService.stopCompletionMessage(taskId,request));
    }


    public LittleResponse feedbackMessage(String messageId, FeedBackRequest request){
        log.debug("feedback completion message messageId {} request: {}", messageId,request);
        return DIfyApiServiceGenerator.executeSync(completionApiService.feedbackMessage(messageId,request));
    }

    public FeedBackListResponse getFeedBackList(Integer page,Integer limit){
        log.debug("get feed back list page {} limit: {}", page,limit);
        return DIfyApiServiceGenerator.executeSync(completionApiService.getAppfeedbacks(page,limit));
    }

    public byte[] textToAudio(TextToAudioRequest request) throws IOException{
        ResponseBody responseBody = DIfyApiServiceGenerator.executeSync(completionApiService.textToAudio(request));
        return responseBody.bytes();
    }

    public Annotation createAnnotation(AnnotationRequest request){
        log.debug("create annotation request: {}", request);
        return DIfyApiServiceGenerator.executeSync(completionApiService.createAnnotation(request));
    }

    public AnnotationListResponse getAnnotationList(Integer page,Integer limit){
        log.debug("get annotation list page {} limit: {}", page,limit);
        return DIfyApiServiceGenerator.executeSync(completionApiService.getAnnotationList(page,limit));
    }

    public void deleteAnnotation(String id){
        log.debug("delete annotation id: {}", id);
        DIfyApiServiceGenerator.executeSync(completionApiService.deleteAnnotation(id));
    }

    public Annotation updateAnnotation(String id,AnnotationRequest request){
        log.debug("update annotation id: {} request: {}", id,request);
        return DIfyApiServiceGenerator.executeSync(completionApiService.updateAnnotation(id,request));
    }

    public AnnotationReplyResponse annotationReply(AnnotationReplyAction action, AnnotationReplyRequest request){
        log.debug("annotation reply action: {} request: {}", action,request);
        return DIfyApiServiceGenerator.executeSync(completionApiService.annotationReply(action,request));
    }

    public AnnotationReplyResponse getAnnotationReplyStatus(AnnotationReplyAction action,String jobId){
        log.debug("get annotation reply status action: {} jobId: {}", action,jobId);
        return DIfyApiServiceGenerator.executeSync(completionApiService.getAnnotationReplyStatus(action,jobId));
    }
}
