package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyDatasetsApi;
import io.github.chatpass.dify.data.request.datasets.CreateDatasetRequest;
import io.github.chatpass.dify.data.request.datasets.CreateDocumentByFileRequest;
import io.github.chatpass.dify.data.request.datasets.ProcessRule;
import io.github.chatpass.dify.data.request.datasets.RetrieveRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateDocumentByFileRequest;
import io.github.chatpass.dify.data.response.datasets.DatasetResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentUploadFileResponse;
import io.github.chatpass.dify.data.response.datasets.RetrieveResponse;
import io.github.chatpass.dify.exception.DifyApiException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;

public class DifyDatasetsApiDocumentByFileExample {

    private static DifyDatasetsApi difyDatasetsApi;

    private static String datasetId;
    private static String documentId;

    public static void main(String[] args) {
        final Map<String, String> envs = System.getenv();
        difyDatasetsApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyDatasetsApi();
        try {
            createDatasetExample();
            createDocumentByFileExample();
            updateDocumentByFileExample();
            getDocumentUploadFileExample();
            retrieveDatasetExample();
            retrieveDatasetWithHybridSearchExample();
            retrieveDatasetWithKeywordSearchExample();
        } catch (DifyApiException | IOException e) {
            e.printStackTrace();
        } finally {
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
                .build();

        DatasetResponse response = difyDatasetsApi.createDataset(request);
        System.out.println("创建知识库响应: " + response);
        datasetId = response.getId();
    }

    private static void createDocumentByFileExample() throws IOException {
        // 创建临时测试文件
        Path tempFile = Files.createTempFile("测试", ".txt");
        Files.write(tempFile, "这是一个测试文件的内容".getBytes());
        File file = tempFile.toFile();

        CreateDocumentByFileRequest request = CreateDocumentByFileRequest.builder()
                .indexingTechnique("high_quality")
                .processRule(ProcessRule.builder()
                        .rules(ProcessRule.Rules.builder()
                                .preProcessingRules(Arrays.asList(
                                        ProcessRule.Rules.PreProcessingRule.builder().id("remove_extra_spaces").enabled(true).build(),
                                        ProcessRule.Rules.PreProcessingRule.builder().id("remove_urls_emails").enabled(true).build()
                                ))
                                .segmentation(ProcessRule.Rules.Segmentation.builder()
                                        .separator("###")
                                        .maxTokens(500)
                                        .build())
                                .build())
                        .mode("custom")
                        .build())
                .docForm("text_model")
                .docLanguage("Chinese")
                .build();

        try {
            DocumentResponse response = difyDatasetsApi.createDocumentByFile(datasetId, file, request);
            System.out.println("通过文件创建文档响应: " + response);
            documentId = response.getDocument().getId();
        } finally {
            // 清理临时文件
            Files.deleteIfExists(tempFile);
        }
    }

    private static void updateDocumentByFileExample() throws IOException {
        // 创建临时测试文件
        Path tempFile = Files.createTempFile("test_update", ".txt");
        Files.write(tempFile, "这是更新后的文件内容".getBytes());
        File file = tempFile.toFile();

        UpdateDocumentByFileRequest request = UpdateDocumentByFileRequest.builder()
                .name("更新后的文档名称")
                .build();

        try {
            DocumentResponse response = difyDatasetsApi.updateDocumentByFile(datasetId, documentId, file, request);
            System.out.println("通过文件更新文档响应: " + response);
        } finally {
            // 清理临时文件
            Files.deleteIfExists(tempFile);
        }
    }

    private static void getDocumentUploadFileExample() {
        DocumentUploadFileResponse response = difyDatasetsApi.getDocumentUploadFile(datasetId, documentId);
        System.out.println("获取上传文件响应: " + response);
    }

    private static void retrieveDatasetExample() {
        RetrieveRequest.RetrievalModel retrievalModel = RetrieveRequest.RetrievalModel.builder()
                .searchMethod("semantic_search")
                .rerankingEnable(false)
                .topK(5)
                .scoreThresholdEnabled(false)
                .build();

        RetrieveRequest request = RetrieveRequest.builder()
                .query("测试查询")
                .retrievalModel(retrievalModel)
                .build();

        RetrieveResponse response = difyDatasetsApi.retrieveDataset(datasetId, request);
        System.out.println("检索查询: " + response.getQuery().getContent());
        System.out.println("检索结果数量: " + response.getRecords().size());
        response.getRecords().forEach(record -> {
            System.out.println("分数: " + record.getScore());
            System.out.println("内容: " + record.getSegment().getContent());
            System.out.println("文档: " + record.getSegment().getDocument().getName());
        });
    }

    private static void retrieveDatasetWithHybridSearchExample() {
        RetrieveRequest.RetrievalModel retrievalModel = RetrieveRequest.RetrievalModel.builder()
                .searchMethod("hybrid_search")
                .rerankingEnable(true)
                .weights(0.7f)
                .topK(10)
                .scoreThresholdEnabled(true)
                .scoreThreshold(0.5f)
                .build();

        RetrieveRequest request = RetrieveRequest.builder()
                .query("混合检索测试")
                .retrievalModel(retrievalModel)
                .build();

        RetrieveResponse response = difyDatasetsApi.retrieveDataset(datasetId, request);
        System.out.println("检索查询: " + response.getQuery().getContent());
        System.out.println("检索结果数量: " + response.getRecords().size());
        response.getRecords().forEach(record -> {
            System.out.println("分数: " + record.getScore());
            System.out.println("内容: " + record.getSegment().getContent());
            System.out.println("文档: " + record.getSegment().getDocument().getName());
        });
    }

    private static void retrieveDatasetWithKeywordSearchExample() {
        RetrieveRequest.RetrievalModel retrievalModel = RetrieveRequest.RetrievalModel.builder()
                .searchMethod("keyword_search")
                .rerankingEnable(false)
                .topK(3)
                .build();

        RetrieveRequest request = RetrieveRequest.builder()
                .query("关键词检索测试")
                .retrievalModel(retrievalModel)
                .build();

        RetrieveResponse response = difyDatasetsApi.retrieveDataset(datasetId, request);
        System.out.println("检索查询: " + response.getQuery().getContent());
        System.out.println("检索结果数量: " + response.getRecords().size());
        response.getRecords().forEach(record -> {
            System.out.println("分数: " + record.getScore());
            System.out.println("内容: " + record.getSegment().getContent());
            System.out.println("文档: " + record.getSegment().getDocument().getName());
        });
    }


    private static void deleteDocument() {
        if(datasetId == null || documentId == null) {
            return;
        }
        difyDatasetsApi.deleteDocument(datasetId, documentId);
        System.out.println("文档删除成功");
    }
    private static void deleteDataset() {
        if(datasetId == null) {
            return;
        }
        difyDatasetsApi.deleteDataset(datasetId);
        System.out.println("知识库删除成功");
    }
}
