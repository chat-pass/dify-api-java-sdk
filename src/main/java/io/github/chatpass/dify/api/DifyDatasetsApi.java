package io.github.chatpass.dify.api;

import io.github.chatpass.dify.data.request.datasets.CreateDatasetRequest;
import io.github.chatpass.dify.data.request.datasets.CreateDocumentByFileRequest;
import io.github.chatpass.dify.data.request.datasets.CreateDocumentByTextRequest;
import io.github.chatpass.dify.data.request.datasets.CreateMetadataRequest;
import io.github.chatpass.dify.data.request.datasets.CreateSegmentsRequest;
import io.github.chatpass.dify.data.request.datasets.RetrieveRequest;
import io.github.chatpass.dify.data.request.datasets.SubmitChildChunkRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateDatasetRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateDocumentByFileRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateDocumentByTextRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateDocumentMetadataRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateMetadataRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateSegmentsRequest;
import io.github.chatpass.dify.data.response.datasets.ChildChunkListResponse;
import io.github.chatpass.dify.data.response.datasets.ChildChunkResponse;
import io.github.chatpass.dify.data.response.datasets.CreateSegmentResponse;
import io.github.chatpass.dify.data.response.datasets.DatasetListResponse;
import io.github.chatpass.dify.data.response.datasets.DatasetMetadataResponse;
import io.github.chatpass.dify.data.response.datasets.DatasetResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentIndexingStatusResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentListResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentUploadFileResponse;
import io.github.chatpass.dify.data.response.datasets.EmbeddingModelListResponse;
import io.github.chatpass.dify.data.response.datasets.MetadataResponse;
import io.github.chatpass.dify.data.response.datasets.RetrieveResponse;
import io.github.chatpass.dify.data.response.datasets.SegmentListResponse;
import io.github.chatpass.dify.data.response.datasets.UpdateSegmentResponse;
import io.github.chatpass.dify.service.DifyDatasetsApiService;
import io.github.chatpass.dify.util.JSONUtils;
import io.github.chatpass.dify.DIfyApiServiceGenerator;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

@Slf4j
public class DifyDatasetsApi {
    private final DifyDatasetsApiService datasetsApiService;

    public DifyDatasetsApi(String baseUrl, String apiKey) {
        this.datasetsApiService = DIfyApiServiceGenerator.createService(DifyDatasetsApiService.class, apiKey, baseUrl);;
    }

    /**
     * 创建空知识库
     * @param request 创建知识库请求参数
     * @return 知识库详情
     */
    public DatasetResponse createDataset(CreateDatasetRequest request) {
        log.debug("create dataset: request={}", request);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.createDataset(request));
    }

    /**
     * 修改知识库详情
     * @param datasetId 知识库ID
     * @param request 修改知识库请求参数
     * @return 知识库详情
     */
    public DatasetResponse updateDataset(String datasetId, UpdateDatasetRequest request) {
        log.debug("update dataset: datasetId={}, request={}", datasetId, request);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.updateDataset(datasetId, request));
    }

    /**
     * 知识库列表
     * @param page 页码
     * @param limit 每页限制
     * @return 数据集列表
     */
    public DatasetListResponse listDataset(Integer page, Integer limit) {
        log.debug("query dataset list");
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.listDataset(page, limit));
    }

    /**
     * 获取知识库详情
     * @param datasetId 知识库ID
     * @return 知识库详情
     */
    public DatasetResponse getDataset(String datasetId) {
        log.info("get dataset info: datasetId={}", datasetId);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.getDataset(datasetId));
    }

    /**
     * 删除知识库
     * @param datasetId 知识库ID
     */
    public void deleteDataset(String datasetId) {
        log.debug("delete dataset: datasetId={}", datasetId);
        DIfyApiServiceGenerator.executeSync(datasetsApiService.deleteDataset(datasetId));
    }

    /**
     * 通过文本创建文档
     * @param datasetId 知识库ID
     * @param request 创建文档请求参数
     * @return 文档详情
     */
    public DocumentResponse createDocumentByText(String datasetId, CreateDocumentByTextRequest request) {
        log.debug("create document by text: datasetId={}, request={}", datasetId, request);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.createDocumentByText(datasetId, request));
    }

    /**
     * 通过文件创建文档
     * @param datasetId 知识库ID
     * @param file 需要上传的文件
     * @param request 创建文档请求参数
     * @return 文档详情
     */
    public DocumentResponse createDocumentByFile(String datasetId, File file, CreateDocumentByFileRequest request) {
        log.debug("create document by file: datasetId={}, fileName={}, request={}", datasetId, file.getName(), request);

        RequestBody fileRequestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);
        MultipartBody.Part dataPart = MultipartBody.Part.createFormData("data", JSONUtils.toJson(request));

        return DIfyApiServiceGenerator.executeSync(datasetsApiService.createDocumentByFile(datasetId, filePart, dataPart));
    }

    /**
     * 通过文本更新文档
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param request 更新文档请求参数
     * @return 知识库详情
     */
    public DocumentResponse updateDocumentByText(String datasetId, String documentId, UpdateDocumentByTextRequest request) {
        log.debug("update document by text: datasetId={}, documentId={}, request={}", datasetId, documentId, request);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.updateDocumentByText(datasetId, documentId, request));
    }

    /**
     * 通过文件更新文档
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param file 需要上传的文件
     * @param request 更新文档请求参数
     * @return 知识库详情
     */
    public DocumentResponse updateDocumentByFile(String datasetId, String documentId, File file, UpdateDocumentByFileRequest request) {
        log.debug("update document by file: datasetId={}, documentId={}, fileName={}, request={}", datasetId, documentId, file.getName(), request);
        RequestBody requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        MultipartBody.Part dataPart = MultipartBody.Part.createFormData("data", JSONUtils.toJson(request));
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.updateDocumentByFile(datasetId, documentId, filePart, dataPart));
    }

    /**
     * 获取文档嵌入状态（进度）
     * @param datasetId 知识库ID
     * @param batch 上传文档的批次号
     * @return 文档索引状态信息
     */
    public DocumentIndexingStatusResponse getDocumentIndexingStatus(String datasetId, String batch) {
        log.debug("get document indexing status: datasetId={}, batch={}", datasetId, batch);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.getDocumentIndexingStatus(datasetId, batch));
    }

    /**
     * 删除文档
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     */
    public void deleteDocument(String datasetId, String documentId) {
        log.debug("delete document: datasetId={}, documentId={}", datasetId, documentId);
        DIfyApiServiceGenerator.executeSync(datasetsApiService.deleteDocument(datasetId, documentId));
    }

    /**
     * 知识库文档列表
     * @param datasetId 知识库ID
     * @param keyword 搜索关键词，可选，目前仅搜索文档名称
     * @param page 页码，可选
     * @param limit 返回条数，可选，默认20，范围1-100
     * @return 文档列表响应
     */
    public DocumentListResponse listDocuments(String datasetId, String keyword, Integer page, Integer limit){
        log.debug("query document list: datasetId={}", datasetId);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.listDocuments(datasetId,keyword,page,limit));
    }

    /**
     * 新增分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param request 新增分段请求参数
     * @return 分段响应
     */
    public CreateSegmentResponse createDocumentSegments(String datasetId, String documentId, CreateSegmentsRequest request) {
        log.debug("create document segments: datasetId={}, documentId={}, request={}", datasetId, documentId, request);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.createDocumentSegments(datasetId, documentId, request));
    }

    /**
     * 更新文档分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 文档分段ID
     * @param request 更新分段请求参数
     * @return 分段更新响应
     */
    public UpdateSegmentResponse updateDocumentSegment(String datasetId, String documentId, String segmentId, UpdateSegmentsRequest request) {
        log.debug("update document segments: datasetId={}, documentId={}, segmentId={} request={}", datasetId, documentId, segmentId, request);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.updateDocumentSegment(datasetId, documentId, segmentId, request));
    }

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
    public SegmentListResponse listDocumentSegments(String datasetId, String documentId, String keyword, String status, Integer page, Integer limit) {
        log.debug("query document segments: datasetId={}, documentId={}, keyword={}", datasetId, documentId, keyword);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.listDocumentSegments(datasetId, documentId, keyword, status, page, limit));
    }

    /**
     * 删除文档分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 文档分段ID
     */
    public void deleteDocumentSegment(String datasetId, String documentId, String segmentId) {
        log.debug("query document segments: datasetId={}, documentId={}, segmentId={}", datasetId, documentId, segmentId);
        DIfyApiServiceGenerator.executeSync(datasetsApiService.deleteDocumentSegment(datasetId, documentId, segmentId));
    }

    /**
     * 新增文档子分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 分段ID
     * @param request 新增子分段请求参数
     * @return 子分段响应
     */
    public ChildChunkResponse createChildChunk(String datasetId, String documentId, String segmentId, SubmitChildChunkRequest request) {
        log.debug("create document child chunk: datasetId={}, documentId={}, segmentId={}, request={}", datasetId, documentId, segmentId, request);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.createChildChunk(datasetId, documentId, segmentId, request));
    }

    /**
     * 更新文档子分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 分段ID
     * @param childChunkId 子分段ID
     * @param request 更新子分段请求参数
     * @return 子分段响应
     */
    public ChildChunkResponse updateChildChunk(String datasetId, String documentId, String segmentId,
                                               String childChunkId, SubmitChildChunkRequest request) {
        log.debug("create document child chunk: datasetId={}, documentId={}, segmentId={}, childChunkId={}, request={}",
                datasetId, documentId, segmentId, childChunkId, request);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.updateChildChunk(datasetId, documentId, segmentId, childChunkId, request));
    }

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
    public ChildChunkListResponse listChildChunks(String datasetId, String documentId, String segmentId,
                                                  String keyword, Integer page, Integer limit) {
        log.debug("query document child chunk list: datasetId={}, documentId={}, segmentId={}, keyword={}",
                datasetId, documentId, segmentId, keyword);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.listChildChunks(datasetId, documentId, segmentId, keyword, page, limit));
    }

    /**
     * 删除文档子分段
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @param segmentId 分段ID
     * @param childChunkId 子分段ID
     */
    public void deleteChildChunk(String datasetId, String documentId, String segmentId, String childChunkId) {
        log.debug("delete document child chunk: datasetId={}, documentId={}, segmentId={}, childChunkId={}",
                datasetId, documentId, segmentId, childChunkId);
        DIfyApiServiceGenerator.executeSync(datasetsApiService.deleteChildChunk(datasetId, documentId, segmentId, childChunkId));
    }

    /**
     * 获取上传文件
     * @param datasetId 知识库ID
     * @param documentId 文档ID
     * @return 上传文件响应
     */
    public DocumentUploadFileResponse getDocumentUploadFile(String datasetId, String documentId) {
        log.info("get document upload file info: datasetId={}, documentId={}", datasetId, documentId);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.getDocumentUploadFile(datasetId, documentId));
    }

    /**
     * 新增元数据
     * @param datasetId 知识库ID
     * @param request 新增元数据请求参数
     * @return 元数据响应
     */
    public MetadataResponse createMetadata(String datasetId, CreateMetadataRequest request) {
        log.info("create dataset metadata: datasetId={}, request={}", datasetId, request);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.createMetadata(datasetId, request));
    }

    /**
     * 更新元数据
     * @param datasetId 知识库ID
     * @param metadataId 元数据ID
     * @param request 更新元数据请求参数
     * @return 元数据响应
     */
    public MetadataResponse updateMetadata(String datasetId, String metadataId, UpdateMetadataRequest request) {
        log.info("update dataset metadata: datasetId={}, metadataId={}, request={}", datasetId, metadataId, request);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.updateMetadata(datasetId, metadataId, request));
    }

    /**
     * 删除元数据
     * @param datasetId 知识库ID
     * @param metadataId 元数据ID
     */
    public void deleteMetadata(String datasetId, String metadataId) {
        log.info("delete dataset metadata: datasetId={}, metadataId={}", datasetId, metadataId);
        DIfyApiServiceGenerator.executeSync(datasetsApiService.deleteMetadata(datasetId, metadataId));
    }

    /**
     * 启用/禁用内置元数据
     * @param datasetId 知识库ID
     * @param action 操作类型（disable/enable）
     */
    public void toggleBuiltInMetadata(String datasetId, String action) {
        DIfyApiServiceGenerator.executeSync(datasetsApiService.toggleBuiltInMetadata(datasetId, action));
    }

    /**
     * 更新文档元数据
     * @param datasetId 知识库ID
     * @param request 更新文档元数据请求参数
     */
    public void updateDocumentMetadata(String datasetId, UpdateDocumentMetadataRequest request) {
        log.debug("update document metadata: datasetId={}, request={}", datasetId, request);
        DIfyApiServiceGenerator.executeSync(datasetsApiService.updateDocumentMetadata(datasetId, request));
    }

    /**
     * 查询知识库元数据列表
     * @param datasetId 知识库ID
     * @return 知识库元数据响应
     */
    public DatasetMetadataResponse listDatasetMetadata(String datasetId) {
        log.debug("query datasets metadata list: datasetId={}", datasetId);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.listDatasetMetadata(datasetId));
    }

    /**
     * 获取嵌入模型列表
     * @return 嵌入模型列表响应
     */
    public EmbeddingModelListResponse listEmbeddingModels() {
        log.debug("get datasets embedding models");
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.listEmbeddingModels());
    }

    /**
     * 检索知识库
     * @param datasetId 知识库ID
     * @param request 检索请求参数
     * @return 检索响应
     */
    public RetrieveResponse retrieveDataset(String datasetId, RetrieveRequest request) {
        log.debug("retrieve dataset: datasetId={}, request={}", datasetId, request);
        return DIfyApiServiceGenerator.executeSync(datasetsApiService.retrieveDataset(datasetId, request));
    }
}
