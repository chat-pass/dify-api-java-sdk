package io.github.chatpass.dify.service;

import io.github.chatpass.dify.data.request.ChatMessageRequest;
import io.github.chatpass.dify.data.request.StopChatMessageRequest;
import io.github.chatpass.dify.data.response.ChatMessageResponse;
import io.github.chatpass.dify.data.response.StopChatMessageResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface DifyChatApiService extends DifyMessageSuggestedApiService,DifyConversationApiService,DifyFeedBackApiService,DifySpeechToTextConversionApiService{

    @POST("chat-messages")
    Call<ChatMessageResponse> sendChatMessage(@Body ChatMessageRequest message);

    @POST("chat-messages")
    @Streaming
    Call<ResponseBody> sendChatMessageStream(@Body ChatMessageRequest message);

    @POST("chat-messages/{task_id}/stop")
    Call<StopChatMessageResponse> stopChatMessage(@Path("task_id")String taskId, @Body StopChatMessageRequest user);
}
