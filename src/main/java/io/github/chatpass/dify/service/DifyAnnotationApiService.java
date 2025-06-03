package io.github.chatpass.dify.service;

import io.github.chatpass.dify.data.enums.AnnotationReplyAction;
import io.github.chatpass.dify.data.request.AnnotationReplyRequest;
import io.github.chatpass.dify.data.request.AnnotationRequest;
import io.github.chatpass.dify.data.response.Annotation;
import io.github.chatpass.dify.data.response.AnnotationListResponse;
import io.github.chatpass.dify.data.response.AnnotationReplyResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DifyAnnotationApiService {


    /**
     * 创建标注
     * @param request
     * @return
     */
    @POST("apps/annotations")
    Call<Annotation> createAnnotation(@Body AnnotationRequest request);

    /**
     * 获取标注列表
     * @param page
     * @param limit
     * @return
     */
    @GET("apps/annotations")
    Call<AnnotationListResponse> getAnnotationList(@Query("page") int page, @Query("limit") int limit);

    /**
     * 删除标注
     * @param id
     * @return
     */
    @DELETE("apps/annotations/{id}")
    Call<Void> deleteAnnotation(@Path("id") String id);

    /**
     * 更新标注
     * @param id
     * @param request
     * @return
     */
    @PUT("apps/annotations/{id}")
    Call<Annotation> updateAnnotation(@Path("id") String id, @Body AnnotationRequest request);


    /**
     * 启用或禁用标注回复设置，并配置嵌入模型。此接口异步执行。
     * 嵌入模型的提供商和模型名称可通过 v1/workspaces/current/models/model-types/text-embedding 接口获取
     * @param action
     * @param request
     * @return
     */
    @POST("apps/annotation-reply/{action}")
    Call<AnnotationReplyResponse> annotationReply(@Path("action") AnnotationReplyAction action, @Body AnnotationReplyRequest request);

    /**
     * 查询标注回复初始设置任务状态
     * @param action 动作，只能是 'enable' 或 'disable'，并且必须和标注回复初始设置接口的动作一致
     * @param jobId 任务 ID，从标注回复初始设置接口返回的 job_id
     * @return
     */
    @GET("apps/annotation-reply/{action}/status/{job_id}")
    Call<AnnotationReplyResponse> getAnnotationReplyStatus(@Path("action") AnnotationReplyAction action, @Path("job_id") String jobId);
} 
