package io.github.chatpass.dify.service;

import io.github.chatpass.dify.data.response.SuggestedQuestionsResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DifyMessageSuggestedApiService {
    @GET("messages/{message_id}/suggested")
    Call<SuggestedQuestionsResponse> getSuggestedQuestions(@Path("message_id") String messageId, @Query("user") String user);
}
