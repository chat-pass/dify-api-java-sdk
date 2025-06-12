package io.github.chatpass.dify.service;

import io.github.chatpass.dify.data.request.datasets.BindingTagRequest;
import io.github.chatpass.dify.data.request.datasets.CreateDatasetRequest;
import io.github.chatpass.dify.data.request.datasets.CreateDocumentByTextRequest;
import io.github.chatpass.dify.data.request.datasets.CreateMetadataRequest;
import io.github.chatpass.dify.data.request.datasets.CreateSegmentsRequest;
import io.github.chatpass.dify.data.request.datasets.CreateTagRequest;
import io.github.chatpass.dify.data.request.datasets.DeleteTagRequest;
import io.github.chatpass.dify.data.request.datasets.RetrieveRequest;
import io.github.chatpass.dify.data.request.datasets.SubmitChildChunkRequest;
import io.github.chatpass.dify.data.request.datasets.UnbindingTagRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateDatasetRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateDocumentByTextRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateDocumentMetadataRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateMetadataRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateSegmentsRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateTagRequest;
import io.github.chatpass.dify.data.response.datasets.ChildChunkListResponse;
import io.github.chatpass.dify.data.response.datasets.ChildChunkResponse;
import io.github.chatpass.dify.data.response.datasets.CreateSegmentResponse;
import io.github.chatpass.dify.data.response.datasets.DatasetListResponse;
import io.github.chatpass.dify.data.response.datasets.DatasetMetadataResponse;
import io.github.chatpass.dify.data.response.datasets.DatasetResponse;
import io.github.chatpass.dify.data.response.datasets.DatasetTagsResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentIndexingStatusResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentListResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentUploadFileResponse;
import io.github.chatpass.dify.data.response.datasets.EmbeddingModelListResponse;
import io.github.chatpass.dify.data.response.datasets.MetadataResponse;
import io.github.chatpass.dify.data.response.datasets.RetrieveResponse;
import io.github.chatpass.dify.data.response.datasets.SegmentListResponse;
import io.github.chatpass.dify.data.response.datasets.TagResponse;
import io.github.chatpass.dify.data.response.datasets.UpdateSegmentResponse;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

/**
 * 知识库 API
 */
public interface DifyDatasetsApiService {

    String DATASETS_PATH = "datasets";

    // 知识库嵌入模型列表
    String DATASETS_EMBEDDING_MODELS_PATH = "workspaces/current/models/model-types/text-embedding";

    /**
     * 创建空知识库
     * @param request 创建知识库请求参数
     * @return 知识库详情
     */
    @POST(DATASETS_PATH)
    Call<DatasetResponse> createDataset(@Body CreateDatasetRequest request);

    /**
     * 修改知识库详情
     * @param datasetId 知识库ID
     * @param request 修改知识库请求参数
     * @return 知识库详情
     */
    @PATCH(DATASETS_PATH + "/{dataset_id}")
    Call<DatasetResponse> updateDataset(@Path("dataset_id") String datasetId,
                                        @Body UpdateDatasetRequest request);

    /**
     * 知识库列表
     * @param page 页码
     * @param limit 每页限制
     * @return 数据集列表
     */
    @GET(DATASETS_PATH)
    Call<DatasetListResponse> listDataset(@Query("page") Integer page,
                                          @Query("limit") Integer limit);

    /**
     * 获取知识库详情
     * @param datasetId 知识库ID
     * @return 知识库详情
     */
    @GET(DATASETS_PATH + "/{dataset_id}")
    Call<DatasetResponse> getDataset(@Path("dataset_id") String datasetId);

    /**
     * 删除知识库
     * @param datasetId 知识库ID
     * @return 无返回内容
     */
    @DELETE(DATASETS_PATH + "/{dataset_id}")
    Call<Void> deleteDataset(@Path("dataset_id") String datasetId);

    /**
     * 通过文本创建文档
     * @param datasetId 知识库ID
     * @param request 创建文档请求参数
     * @return 文档详情
     */
    @POST(DATASETS_PATH + "/{dataset_id}/document/create-by-text")
    Call<DocumentResponse> createDocumentByText(@Path("dataset_id") String datasetId,
                                                @Body CreateDocumentByTextRequest request);

    /**
     * 通过文件创建文档
     * @param datasetId 知识库ID
     * @return 文档详情
     */
    @Multipart
    @POST(DATASETS_PATH + "/{dataset_id}/document/create-by-file")
    Call<DocumentResponse> createDocumentByFile(@Path("dataset_id") String datasetId,
                                                @Part MultipartBody.Part file,
                                                @Part MultipartBody.Part data);

    /**
     * 通过文本更新文档
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param request 更新文档请求参数
     * @return 知识库详情
     */
    @POST(DATASETS_PATH + "/{dataset_id}/documents/{document_id}/update-by-text")
    Call<DocumentResponse> updateDocumentByText(@Path("dataset_id") String datasetId,
                                               @Path("document_id") String documentId,
                                               @Body UpdateDocumentByTextRequest request);

    /**
     * 通过文件更新文档
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param file 需要上传的文件
     * @return 知识库详情
     */
    @Multipart
    @POST(DATASETS_PATH + "/{dataset_id}/documents/{document_id}/update-by-file")
    Call<DocumentResponse> updateDocumentByFile(@Path("dataset_id") String datasetId,
                                               @Path("document_id") String documentId,
                                               @Part MultipartBody.Part file,
                                               @Part MultipartBody.Part data);

    /**
     * 获取文档嵌入状态（进度）
     * @param datasetId 知识库ID
     * @param batch 上传文档的批次号
     * @return 文档索引状态信息
     */
    @GET(DATASETS_PATH + "/{dataset_id}/documents/{batch}/indexing-status")
    Call<DocumentIndexingStatusResponse> getDocumentIndexingStatus(@Path("dataset_id") String datasetId,
                                                                   @Path("batch") String batch);

    /**
     * 删除文档
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @return 无返回内容
     */
    @DELETE(DATASETS_PATH + "/{dataset_id}/documents/{document_id}")
    Call<Void> deleteDocument(@Path("dataset_id") String datasetId,
                              @Path("document_id") String documentId);

    /**
     * 知识库文档列表
     * @param datasetId 知识库ID
     * @param keyword 搜索关键词，可选，目前仅搜索文档名称
     * @param page 页码，可选
     * @param limit 返回条数，可选，默认20，范围1-100
     * @return 文档列表响应
     */
    @GET(DATASETS_PATH + "/{dataset_id}/documents")
    Call<DocumentListResponse> listDocuments(@Path("dataset_id") String datasetId, 
                                           @Query("keyword") String keyword, 
                                           @Query("page") Integer page,
                                           @Query("limit") Integer limit);

    /**
     * 新增分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param request 新增分段请求参数
     * @return 分段响应
     */
    @POST(DATASETS_PATH + "/{dataset_id}/documents/{document_id}/segments")
    Call<CreateSegmentResponse> createDocumentSegments(@Path("dataset_id") String datasetId,
                                               @Path("document_id") String documentId,
                                               @Body CreateSegmentsRequest request);

    /**
     * 更新文档分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 文档分段ID
     * @param request 更新分段请求参数
     * @return 分段更新响应
     */
    @POST(DATASETS_PATH + "/{dataset_id}/documents/{document_id}/segments/{segment_id}")
    Call<UpdateSegmentResponse> updateDocumentSegment(@Path("dataset_id") String datasetId,
                                                      @Path("document_id") String documentId,
                                                      @Path("segment_id") String segmentId,
                                                      @Body UpdateSegmentsRequest request);

    /**
     * 查询文档分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param keyword 搜索关键词，可选
     * @param status 搜索状态，completed
     * @param page 页码，可选
     * @param limit 返回条数，可选，默认20，范围1-100
     * @return 分段列表响应
     */
    @GET(DATASETS_PATH + "/{dataset_id}/documents/{document_id}/segments")
    Call<SegmentListResponse> listDocumentSegments(@Path("dataset_id") String datasetId,
                                                   @Path("document_id") String documentId,
                                                   @Query("keyword") String keyword,
                                                   @Query("status") String status,
                                                   @Query("page") Integer page,
                                                   @Query("limit") Integer limit);

    /**
     * 删除文档分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 文档分段ID
     * @return 无返回内容
     */
    @DELETE(DATASETS_PATH + "/{dataset_id}/documents/{document_id}/segments/{segment_id}")
    Call<Void> deleteDocumentSegment(@Path("dataset_id") String datasetId,
                                    @Path("document_id") String documentId,
                                    @Path("segment_id") String segmentId);

    /**
     * 新增文档子分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 分段ID
     * @param request 新增子分段请求参数
     * @return 子分段响应
     */
    @POST(DATASETS_PATH + "/{dataset_id}/documents/{document_id}/segments/{segment_id}/child_chunks")
    Call<ChildChunkResponse> createChildChunk(@Path("dataset_id") String datasetId,
                                            @Path("document_id") String documentId,
                                            @Path("segment_id") String segmentId,
                                            @Body SubmitChildChunkRequest request);

    /**
     * 更新文档子分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 分段ID
     * @param childChunkId 子分段ID
     * @param request 更新子分段请求参数
     * @return 子分段响应
     */
    @PATCH(DATASETS_PATH + "/{dataset_id}/documents/{document_id}/segments/{segment_id}/child_chunks/{child_chunk_id}")
    Call<ChildChunkResponse> updateChildChunk(@Path("dataset_id") String datasetId,
                                            @Path("document_id") String documentId,
                                            @Path("segment_id") String segmentId,
                                            @Path("child_chunk_id") String childChunkId,
                                            @Body SubmitChildChunkRequest request);

    /**
     * 查询文档子分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 分段ID
     * @param keyword 搜索关键词（选填）
     * @param page 页码（选填，默认1）
     * @param limit 每页数量（选填，默认20，最大100）
     * @return 子分段列表响应
     */
    @GET(DATASETS_PATH + "/{dataset_id}/documents/{document_id}/segments/{segment_id}/child_chunks")
    Call<ChildChunkListResponse> listChildChunks(@Path("dataset_id") String datasetId,
                                               @Path("document_id") String documentId,
                                               @Path("segment_id") String segmentId,
                                               @Query("keyword") String keyword,
                                               @Query("page") Integer page,
                                               @Query("limit") Integer limit);

    /**
     * 删除文档子分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 分段ID
     * @param childChunkId 子分段ID
     * @return 无返回内容
     */
    @DELETE(DATASETS_PATH + "/{dataset_id}/documents/{document_id}/segments/{segment_id}/child_chunks/{child_chunk_id}")
    Call<Void> deleteChildChunk(@Path("dataset_id") String datasetId,
                               @Path("document_id") String documentId,
                               @Path("segment_id") String segmentId,
                               @Path("child_chunk_id") String childChunkId);

    /**
     * 获取上传文件
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @return 上传文件响应
     */
    @GET(DATASETS_PATH + "/{dataset_id}/documents/{document_id}/upload-file")
    Call<DocumentUploadFileResponse> getDocumentUploadFile(@Path("dataset_id") String datasetId,
                                                           @Path("document_id") String documentId);

    /**
     * 新增元数据
     * @param datasetId 知识库ID
     * @param request 新增元数据请求参数
     * @return 元数据响应
     */
    @POST(DATASETS_PATH + "/{dataset_id}/metadata")
    Call<MetadataResponse> createMetadata(@Path("dataset_id") String datasetId,
                                          @Body CreateMetadataRequest request);

    /**
     * 更新元数据
     * @param datasetId 知识库ID
     * @param metadataId 元数据ID
     * @param request 更新元数据请求参数
     * @return 元数据响应
     */
    @PATCH(DATASETS_PATH + "/{dataset_id}/metadata/{metadata_id}")
    Call<MetadataResponse> updateMetadata(@Path("dataset_id") String datasetId,
                                        @Path("metadata_id") String metadataId,
                                        @Body UpdateMetadataRequest request);

    /**
     * 删除元数据
     * @param datasetId 知识库ID
     * @param metadataId 元数据ID
     * @return 无返回内容
     */
    @DELETE(DATASETS_PATH + "/{dataset_id}/metadata/{metadata_id}")
    Call<Void> deleteMetadata(@Path("dataset_id") String datasetId,
                             @Path("metadata_id") String metadataId);

    /**
     * 启用/禁用内置元数据
     * @param datasetId 知识库ID
     * @param action 操作类型（disable/enable）
     * @return 无返回内容
     */
    @POST(DATASETS_PATH + "/{dataset_id}/metadata/built-in/{action}")
    Call<Void> toggleBuiltInMetadata(@Path("dataset_id") String datasetId,
                                    @Path("action") String action);

    /**
     * 更新文档元数据
     * @param datasetId 知识库ID
     * @param request 更新文档元数据请求参数
     * @return 无返回内容
     */
    @POST(DATASETS_PATH + "/{dataset_id}/documents/metadata")
    Call<Void> updateDocumentMetadata(@Path("dataset_id") String datasetId,
                                    @Body UpdateDocumentMetadataRequest request);

    /**
     * 查询知识库元数据列表
     * @param datasetId 知识库ID
     * @return 知识库元数据响应
     */
    @GET(DATASETS_PATH + "/{dataset_id}/metadata")
    Call<DatasetMetadataResponse> listDatasetMetadata(@Path("dataset_id") String datasetId);

    /**
     * 获取嵌入模型列表
     * @return 嵌入模型列表响应
     */
    @GET(DATASETS_EMBEDDING_MODELS_PATH)
    Call<EmbeddingModelListResponse> listEmbeddingModels();

    /**
     * 检索知识库
     * @param datasetId 知识库ID
     * @param request 检索请求参数
     * @return 检索响应
     */
    @POST(DATASETS_PATH + "/{dataset_id}/retrieve")
    Call<RetrieveResponse> retrieveDataset(@Path("dataset_id") String datasetId,
                                         @Body RetrieveRequest request);

    /**
     * 新增知识库类型标签
     * @param request 新增标签请求参数
     * @return 标签响应
     */
    @POST(DATASETS_PATH + "/tags")
    Call<TagResponse> createTag(@Body CreateTagRequest request);

    /**
     * 获取知识库类型标签列表
     * @return 标签列表
     */
    @GET(DATASETS_PATH + "/tags")
    Call<List<TagResponse>> listTags();

    /**
     * 修改知识库类型标签名称
     * @param request 修改标签请求参数
     * @return 标签响应
     */
    @PATCH(DATASETS_PATH + "/tags")
    Call<TagResponse> updateTag(@Body UpdateTagRequest request);

    /**
     * 删除知识库类型标签
     * @param request 删除标签请求参数
     * @return 无返回内容
     */
    @HTTP(method = "DELETE", path = DATASETS_PATH + "/tags", hasBody = true)
    Call<Void> deleteTag(@Body DeleteTagRequest request);

    /**
     * 绑定知识库到知识库类型标签
     * @param request 绑定标签请求参数
     * @return 无返回内容
     */
    @POST(DATASETS_PATH + "/tags/binding")
    Call<Void> bindingTag(@Body BindingTagRequest request);

    /**
     * 解绑知识库和知识库类型标签
     * @param request 解绑标签请求参数
     * @return 无返回内容
     */
    @POST(DATASETS_PATH + "/tags/unbinding")
    Call<Void> unbindingTag(@Body UnbindingTagRequest request);

    /**
     * 查询知识库已绑定的标签
     * @param datasetId 知识库ID
     * @return 知识库标签响应
     */
    @GET(DATASETS_PATH + "/{dataset_id}/tags")
    Call<DatasetTagsResponse> getDatasetTags(@Path("dataset_id") String datasetId);

}
