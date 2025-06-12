package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyDatasetsApi;
import io.github.chatpass.dify.data.request.datasets.BindingTagRequest;
import io.github.chatpass.dify.data.request.datasets.CreateDatasetRequest;
import io.github.chatpass.dify.data.request.datasets.CreateDocumentByTextRequest;
import io.github.chatpass.dify.data.request.datasets.CreateMetadataRequest;
import io.github.chatpass.dify.data.request.datasets.CreateSegmentsRequest;
import io.github.chatpass.dify.data.request.datasets.CreateTagRequest;
import io.github.chatpass.dify.data.request.datasets.DeleteTagRequest;
import io.github.chatpass.dify.data.request.datasets.ProcessRule;
import io.github.chatpass.dify.data.request.datasets.RetrievalModel;
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
import io.github.chatpass.dify.data.response.datasets.MetadataResponse;
import io.github.chatpass.dify.data.response.datasets.SegmentListResponse;
import io.github.chatpass.dify.data.response.datasets.TagResponse;
import io.github.chatpass.dify.data.response.datasets.UpdateSegmentResponse;
import io.github.chatpass.dify.exception.DifyApiError;
import io.github.chatpass.dify.exception.DifyApiException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DifyDatasetsApiExample {

    private static DifyDatasetsApi difyDatasetsApi;

    private static String datasetId;
    private static String documentId;
    private static String segmentId;
    private static String childChunkId;
    private static String metadataId;
    private static String tagId;

    public static void main(String[] args) {
        final Map<String, String> envs = System.getenv();
        difyDatasetsApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyDatasetsApi();
        try {
            createDatasetExample();
            updateDatasetExample();
            getDatasetListExample();
            getDatasetExample();

            // 文档管理示例
            String batch = createDocumentByTextExample();
            getDocumentIndexingStatusExample(batch);
            updateDocumentByTextExample();
            listDocumentsExample();
            listDocumentsWithKeywordExample();

            // 分段管理示例
            createDocumentSegmentsExample();
            updateDocumentSegmentExample();
            listDocumentSegmentsExample();

            // 子分段管理示例
            createChildChunkExample();
            updateChildChunkExample();
            listChildChunksExample();

            // 元数据管理示例
            createMetadataExample();
            updateMetadataExample();
            toggleBuiltInMetadataExample();;
            updateDocumentMetadataExample();
            listDatasetMetadataExample();

            // 标签管理示例
            createTagExample();
            listTagsExample();
            updateTagExample();
            bindingTagExample();
            getDatasetTagsExample();
            unbindingTagExample();
        } catch (DifyApiException e) {
            e.printStackTrace();
        } finally {
            deleteTagExample();
            deleteMetadata();
            deleteChildChunk();
            deleteDocumentSegment();
            deleteDocument();
            deleteDataset();
        }
    }

    private static void createDatasetExample() {
        CreateDatasetRequest request = CreateDatasetRequest.builder()
                .name("测试知识库" + System.currentTimeMillis())
                .description("这是一个测试知识库")
                .indexingTechnique("high_quality")
                .permission("only_me")
                .provider("vendor")
                .retrievalModel(RetrievalModel.builder()
                        .searchMethod("hybrid_search")
                        .rerankingEnable(false)
                        .scoreThresholdEnabled(false)
                        .topK(2)
                        .build())
                .build();

        DatasetResponse response = difyDatasetsApi.createDataset(request);
        System.out.println("创建知识库响应: " + response);
        datasetId = response.getId();
    }

    private static void updateDatasetExample() {
        UpdateDatasetRequest request = UpdateDatasetRequest.builder()
                .name("更新后的测试知识库")
                .description("这是一个更新后的测试知识库")
                .indexingTechnique("high_quality")
                .permission("only_me")
                .build();

        DatasetResponse response = difyDatasetsApi.updateDataset(datasetId, request);
        System.out.println("更新知识库响应: " + response);
    }

    private static void getDatasetListExample() {
        DatasetListResponse response = difyDatasetsApi.listDataset(1, 10);
        System.out.println("知识库列表响应: " + response);
    }

    private static void getDatasetExample() {
        DatasetResponse response = difyDatasetsApi.getDataset(datasetId);
        System.out.println("获取知识库详情响应: " + response);
    }

    private static String createDocumentByTextExample() {
        CreateDocumentByTextRequest request = CreateDocumentByTextRequest.builder()
                .name("测试文档" + System.currentTimeMillis())
                .text("这是一个测试文档的内容。包含了一些测试数据用于验证文档创建功能。")
                .indexingTechnique("high_quality")
                .docForm("text_model")
                .docLanguage("Chinese")
                .processRule(ProcessRule.builder()
                        .mode("automatic").build())
                .retrievalModel(RetrievalModel.builder()
                        .searchMethod("hybrid_search")
                        .rerankingEnable(false)
                        .scoreThresholdEnabled(false)
                        .topK(2)
                        .build())
                .build();

        DocumentResponse response = difyDatasetsApi.createDocumentByText(datasetId, request);
        System.out.println("通过文本创建文档响应: " + response);
        documentId = response.getDocument().getId();
        String batch = response.getBatch();
        // 等待索引完成
        try {
            System.out.println("等待文档索引完成...");
            Thread.sleep(5000); // 等待5秒
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return batch;
    }

    private static void updateDocumentByTextExample() {
        UpdateDocumentByTextRequest request = UpdateDocumentByTextRequest.builder()
                .name("更新后的测试文档")
                .text("这是更新后的文档内容。")
                .build();

        DocumentResponse response = difyDatasetsApi.updateDocumentByText(datasetId, documentId, request);
        System.out.println("通过文本更新文档响应: " + response);
    }

    private static void getDocumentIndexingStatusExample(String batch) {
        DocumentIndexingStatusResponse response = difyDatasetsApi.getDocumentIndexingStatus(datasetId, batch);
        System.out.println("获取文档索引状态响应: " + response);
    }

    private static void listDocumentsExample() {
        DocumentListResponse response = difyDatasetsApi.listDocuments(datasetId, null, 1, 10);
        System.out.println("知识库文档列表响应: " + response);
    }

    private static void listDocumentsWithKeywordExample() {
        DocumentListResponse response = difyDatasetsApi.listDocuments(datasetId, "测试", 1, 5);
        System.out.println("带关键词的文档列表响应: " + response);
    }

    private static void createDocumentSegmentsExample(){
        CreateSegmentsRequest.Segment segment = CreateSegmentsRequest.Segment.builder()
                .content("这是一个测试分段的内容")
                .answer("这是一个测试分段的内容")
                .keywords(Arrays.asList("测试", "分段"))
                .build();

        CreateSegmentsRequest request = CreateSegmentsRequest.builder()
                .segments(Collections.singletonList(segment))
                .build();

        CreateSegmentResponse response = difyDatasetsApi.createDocumentSegments(datasetId, documentId, request);
        System.out.println("创建文档分段响应: " + response);
        if(response != null && response.getData() != null && response.getData().size() > 0) {
            segmentId = response.getData().get(0).getId();
        } else {
            throw new DifyApiException(new DifyApiError(-1, "创建文档分段异常"));
        }
    }

    private static void updateDocumentSegmentExample() {
        UpdateSegmentsRequest.Segment segment = UpdateSegmentsRequest.Segment.builder()
                .content("这是更新后的分段内容")
                .keywords(Arrays.asList("更新", "分段"))
                .enabled(true)
                .regenerateChildChunks(false)
                .build();

        UpdateSegmentsRequest request = UpdateSegmentsRequest.builder()
                .segment(segment)
                .build();

        UpdateSegmentResponse response = difyDatasetsApi.updateDocumentSegment(datasetId, documentId, segmentId, request);
        System.out.println("更新文档分段响应: " + response);
    }

    private static void listDocumentSegmentsExample() {
        SegmentListResponse response = difyDatasetsApi.listDocumentSegments(datasetId, documentId, "测试", "completed", 1, 5);
        System.out.println("带关键词的文档分段列表响应: " + response);
    }

    private static void createChildChunkExample() {
        SubmitChildChunkRequest request = SubmitChildChunkRequest.builder()
                .content("这是一个测试子分段的内容")
                .build();

        ChildChunkResponse response = difyDatasetsApi.createChildChunk(datasetId, documentId, segmentId, request);
        System.out.println("创建子分段响应: " + response);
        if(response != null && response.getData() != null) {
            childChunkId = response.getData().getId();
        } else {
            throw new DifyApiException(new DifyApiError(-1, "创建子分段异常"));
        }
    }

    private static void updateChildChunkExample() {
        SubmitChildChunkRequest request = SubmitChildChunkRequest.builder()
                .content("这是更新后的子分段内容")
                .build();

        ChildChunkResponse response = difyDatasetsApi.updateChildChunk(datasetId, documentId, segmentId, childChunkId, request);
        System.out.println("更新子分段响应: " + response);
    }

    private static void listChildChunksExample() {
        ChildChunkListResponse response = difyDatasetsApi.listChildChunks(datasetId, documentId, segmentId, "测试", 1, 5);
        System.out.println("带关键词的子分段列表响应: " + response);
    }

    private static void createMetadataExample() {
        CreateMetadataRequest request = CreateMetadataRequest.builder()
                .type("string")
                .name("测试元数据")
                .build();

        MetadataResponse response = difyDatasetsApi.createMetadata(datasetId, request);
        System.out.println("创建元数据响应: " + response);
        metadataId = response.getId();
    }

    private static void updateMetadataExample() {
        UpdateMetadataRequest request = UpdateMetadataRequest.builder()
                .name("更新后的元数据名称")
                .build();

        MetadataResponse response = difyDatasetsApi.updateMetadata(datasetId, metadataId, request);
        System.out.println("更新元数据响应: " + response);
    }

    private static void toggleBuiltInMetadataExample() {
        // 禁用内置元数据
        difyDatasetsApi.toggleBuiltInMetadata(datasetId, "disable");
        System.out.println("内置元数据已禁用");

        // 启用内置元数据
        difyDatasetsApi.toggleBuiltInMetadata(datasetId, "enable");
        System.out.println("内置元数据已启用");
    }

    private static void updateDocumentMetadataExample() {
        UpdateDocumentMetadataRequest.MetadataItem metadataItem = UpdateDocumentMetadataRequest.MetadataItem.builder()
                .id(metadataId)
                .name("测试元数据")
                .value("测试值")
                .build();

        UpdateDocumentMetadataRequest.OperationData operationData = UpdateDocumentMetadataRequest.OperationData.builder()
                .documentId(documentId)
                .metadataList(Collections.singletonList(metadataItem))
                .build();

        UpdateDocumentMetadataRequest request = UpdateDocumentMetadataRequest.builder()
                .operationData(Collections.singletonList(operationData))
                .build();

        difyDatasetsApi.updateDocumentMetadata(datasetId, request);
        System.out.println("文档元数据更新成功");
    }

    private static void listDatasetMetadataExample() {
        DatasetMetadataResponse response = difyDatasetsApi.listDatasetMetadata(datasetId);
        System.out.println("知识库元数据列表响应: " + response);
    }

    private static void createTagExample() {
        CreateTagRequest request = CreateTagRequest.builder()
                .name("测试标签" + System.currentTimeMillis())
                .build();

        TagResponse response = difyDatasetsApi.createTag(request);
        System.out.println("创建标签响应: " + response);
        tagId = response.getId();
    }

    private static void listTagsExample() {
        List<TagResponse> response = difyDatasetsApi.listTags();
        System.out.println("标签列表响应: " + response);
    }

    private static void updateTagExample() {
        UpdateTagRequest request = UpdateTagRequest.builder()
                .name("更新后的测试标签")
                .tagId(tagId)
                .build();

        TagResponse response = difyDatasetsApi.updateTag(request);
        System.out.println("更新标签响应: " + response);
    }

    private static void bindingTagExample() {
        BindingTagRequest request = BindingTagRequest.builder()
                .tagIds(Collections.singletonList(tagId))
                .targetId(datasetId)
                .build();

        difyDatasetsApi.bindingTag(request);
        System.out.println("绑定标签成功");
    }

    private static void getDatasetTagsExample() {
        DatasetTagsResponse response = difyDatasetsApi.getDatasetTags(datasetId);
        System.out.println("查询知识库标签响应: " + response);
    }

    private static void unbindingTagExample() {
        UnbindingTagRequest request = UnbindingTagRequest.builder()
                .tagId(tagId)
                .targetId(datasetId)
                .build();

        difyDatasetsApi.unbindingTag(request);
        System.out.println("解绑标签成功");
    }

    private static void deleteMetadata() {
        if(datasetId == null || metadataId == null) {
            return;
        }
        difyDatasetsApi.deleteMetadata(datasetId, metadataId);
        System.out.println("元数据删除成功");
    }

    private static void deleteChildChunk() {
        if(datasetId == null || documentId == null || segmentId == null || childChunkId == null) {
            return;
        }
        difyDatasetsApi.deleteChildChunk(datasetId, documentId, segmentId, childChunkId);
        System.out.println("子分段删除成功");
    }
    private static void deleteDocumentSegment() {
        if(datasetId == null || documentId == null || segmentId == null) {
            return;
        }
        difyDatasetsApi.deleteDocumentSegment(datasetId, documentId, segmentId);
        System.out.println("文档分段删除成功");
    }
    private static void deleteDocument() {
        if(datasetId == null || documentId == null) {
            return;
        }
        difyDatasetsApi.deleteDocument(datasetId, documentId);
        System.out.println("文档删除成功");
    }

    private static void deleteTagExample() {
        if(tagId == null) {
            return;
        }
        DeleteTagRequest request = DeleteTagRequest.builder()
                .tagId(tagId)
                .build();

        difyDatasetsApi.deleteTag(request);
        System.out.println("标签删除成功");
    }

    private static void deleteDataset() {
        if(datasetId == null) {
            return;
        }
        difyDatasetsApi.deleteDataset(datasetId);
        System.out.println("知识库删除成功");
    }
}
