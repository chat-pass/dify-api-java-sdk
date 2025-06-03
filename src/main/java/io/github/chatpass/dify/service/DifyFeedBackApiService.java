package io.github.chatpass.dify.service;

import io.github.chatpass.dify.data.request.FeedBackRequest;
import io.github.chatpass.dify.data.response.FeedBackListResponse;
import io.github.chatpass.dify.data.response.LittleResponse;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * 消息反馈
 */
public interface DifyFeedBackApiService {

    /**
     * 消息反馈（点赞）
     * @return
     */
    @POST("messages/{message_id}/feedbacks")
    Call<LittleResponse> feedbackMessage(@Path("message_id") String messageId, @Body FeedBackRequest request);

    /**
     * 获取APP的消息点赞和反馈
     * @param page
     * @param limit
     * @return
     */
    @GET("app/feedbacks")
    Call<FeedBackListResponse> getAppfeedbacks(@Query("page") Integer page, @Query("limit") Integer limit);
}
