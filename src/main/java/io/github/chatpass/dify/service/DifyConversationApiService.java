package io.github.chatpass.dify.service;

import io.github.chatpass.dify.data.request.RenameConversationRequest;
import io.github.chatpass.dify.data.request.SimpleUserRequest;
import io.github.chatpass.dify.data.response.*;
import io.github.chatpass.dify.data.response.*;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * 会话管理接口
 */
public interface DifyConversationApiService {

    /**
     * 获取会话列表
     */
    @GET("conversations")
    Call<ConversationListResponse> getConversations(@Query("user") String user,
                                                    @Query("last_id") String lastId,
                                                    @Query("limit") Integer limit,
                                                    @Query("sort_by") String sortBy);
    @GET("messages")
    Call<ChatMessageListResponse> getMessages(@Query("conversation_id") String conversationId,
                                              @Query("user") String user,
                                              @Query("first_id") String firstId,
                                              @Query("limit") Integer limit);

    /**
     * 删除会话
     * @param conversationId
     * @param user
     * @return
     */
    @HTTP(method = "DELETE", path = "conversations/{conversation_id}", hasBody = true)
    Call<LittleResponse> deleteConversation(@Path("conversation_id") String conversationId, @Body SimpleUserRequest user);

    /**
     * 会话重命名
     * @param conversationId
     * @param request
     * @return
     */
    @POST("conversations/{conversation_id}/name")
    Call<Conversation> renameConversation(@Path("conversation_id") String conversationId, @Body RenameConversationRequest
                                           request);

    /**
     * 获取对话变量
     * @param conversationId 会话 ID。
     * @param user 用户标识，由开发者定义规则，需保证用户标识在应用内唯一
     * @param lastId 选填）当前页最后面一条记录的 ID，默认 null。
     * @param limit 一次请求返回多少条记录，默认 20 条，最大 100 条，最小 1 条。
     * @param variableName 选填）按变量名称筛选
     * @return
     */
    @GET("conversations/{conversation_id}/variables")
    Call<ConversationVariablesResponse> getConversationVariables(@Path("conversation_id") String conversationId,
                                                                 @Query("user") String user,
                                                                 @Query("last_id") String lastId,
                                                                 @Query("limit") Integer limit,
                                                                 @Query("variable_name") String variableName);
}
