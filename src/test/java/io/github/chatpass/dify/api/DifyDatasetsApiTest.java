package io.github.chatpass.dify.api;

import io.github.chatpass.dify.data.request.datasets.CreateDatasetRequest;
import io.github.chatpass.dify.data.request.datasets.CreateDocumentByTextRequest;
import io.github.chatpass.dify.data.request.datasets.CreateMetadataRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateDatasetRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateDocumentByTextRequest;
import io.github.chatpass.dify.data.request.datasets.UpdateMetadataRequest;
import io.github.chatpass.dify.data.response.datasets.DatasetListResponse;
import io.github.chatpass.dify.data.response.datasets.DatasetResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentIndexingStatusResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentListResponse;
import io.github.chatpass.dify.data.response.datasets.DocumentResponse;
import io.github.chatpass.dify.data.response.datasets.EmbeddingModelListResponse;
import io.github.chatpass.dify.data.response.datasets.MetadataResponse;
import okhttp3.mockwebserver.MockResponse;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DifyDatasetsApiTest extends MockServerUtilsTest {

    @Test
    public void testCreateDataset() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "  \"name\": \"测试知识库\",\n" +
                "  \"description\": \"这是一个测试知识库\",\n" +
                "  \"provider\": \"vendor\",\n" +
                "  \"permission\": \"only_me\",\n" +
                "  \"data_source_type\": \"upload_file\",\n" +
                "  \"indexing_technique\": \"high_quality\",\n" +
                "  \"app_count\": 0,\n" +
                "  \"document_count\": 0,\n" +
                "  \"word_count\": 0,\n" +
                "  \"created_by\": \"user123\",\n" +
                "  \"created_at\": 1672531199,\n" +
                "  \"updated_by\": \"user123\",\n" +
                "  \"updated_at\": 1672531199\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 创建请求对象
        CreateDatasetRequest request = CreateDatasetRequest.builder()
                .name("测试知识库")
                .description("这是一个测试知识库")
                .indexingTechnique("high_quality")
                .permission("only_me")
                .provider("vendor")
                .build();

        // 调用方法
        DatasetResponse response = difyDatasetsApi.createDataset(request);

        // 验证响应
        assertEquals("3c90c3cc-0d44-4b50-8888-8dd25736052a", response.getId());
        assertEquals("测试知识库", response.getName());
        assertEquals("这是一个测试知识库", response.getDescription());
        assertEquals("high_quality", response.getIndexingTechnique());
    }

    @Test
    public void testUpdateDataset() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "  \"name\": \"更新后的知识库\",\n" +
                "  \"description\": \"这是更新后的知识库描述\",\n" +
                "  \"provider\": \"vendor\",\n" +
                "  \"permission\": \"all_team_members\",\n" +
                "  \"data_source_type\": \"upload_file\",\n" +
                "  \"indexing_technique\": \"economy\",\n" +
                "  \"app_count\": 1,\n" +
                "  \"document_count\": 5,\n" +
                "  \"word_count\": 1000,\n" +
                "  \"created_by\": \"user123\",\n" +
                "  \"created_at\": 1672531199,\n" +
                "  \"updated_by\": \"user456\",\n" +
                "  \"updated_at\": 1672617599\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 创建请求对象
        UpdateDatasetRequest request = UpdateDatasetRequest.builder()
                .name("更新后的知识库")
                .description("这是更新后的知识库描述")
                .indexingTechnique("economy")
                .permission("all_team_members")
                .build();

        // 调用方法
        DatasetResponse response = difyDatasetsApi.updateDataset("3c90c3cc-0d44-4b50-8888-8dd25736052a", request);

        // 验证响应
        assertEquals("更新后的知识库", response.getName());
        assertEquals("这是更新后的知识库描述", response.getDescription());
        assertEquals("economy", response.getIndexingTechnique());
        assertEquals("all_team_members", response.getPermission());
    }

    @Test
    public void testListDataset() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": \"dataset1\",\n" +
                "      \"name\": \"知识库1\",\n" +
                "      \"description\": \"第一个知识库\",\n" +
                "      \"provider\": \"vendor\",\n" +
                "      \"permission\": \"only_me\",\n" +
                "      \"data_source_type\": \"upload_file\",\n" +
                "      \"indexing_technique\": \"high_quality\",\n" +
                "      \"app_count\": 2,\n" +
                "      \"document_count\": 10,\n" +
                "      \"word_count\": 5000,\n" +
                "      \"created_by\": \"user123\",\n" +
                "      \"created_at\": 1672531199\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"dataset2\",\n" +
                "      \"name\": \"知识库2\",\n" +
                "      \"description\": \"第二个知识库\",\n" +
                "      \"provider\": \"vendor\",\n" +
                "      \"permission\": \"all_team_members\",\n" +
                "      \"data_source_type\": \"upload_file\",\n" +
                "      \"indexing_technique\": \"economy\",\n" +
                "      \"app_count\": 1,\n" +
                "      \"document_count\": 3,\n" +
                "      \"word_count\": 1500,\n" +
                "      \"created_by\": \"user456\",\n" +
                "      \"created_at\": 1672617599\n" +
                "    }\n" +
                "  ],\n" +
                "  \"has_more\": false,\n" +
                "  \"limit\": 20,\n" +
                "  \"total\": 2,\n" +
                "  \"page\": 1\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 调用方法
        DatasetListResponse response = difyDatasetsApi.listDataset(1, 20);

        // 验证响应
        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
        assertEquals("知识库1", response.getData().get(0).getName());
        assertEquals("知识库2", response.getData().get(1).getName());
        assertEquals(Integer.valueOf(2), response.getTotal());
        assertEquals(false, response.getHasMore());
    }

    @Test
    public void testGetDataset() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"id\": \"3c90c3cc-0d44-4b50-8888-8dd25736052a\",\n" +
                "  \"name\": \"测试知识库\",\n" +
                "  \"description\": \"这是一个测试知识库\",\n" +
                "  \"provider\": \"vendor\",\n" +
                "  \"permission\": \"only_me\",\n" +
                "  \"data_source_type\": \"upload_file\",\n" +
                "  \"indexing_technique\": \"high_quality\",\n" +
                "  \"app_count\": 3,\n" +
                "  \"document_count\": 15,\n" +
                "  \"word_count\": 8000,\n" +
                "  \"created_by\": \"user123\",\n" +
                "  \"created_at\": 1672531199,\n" +
                "  \"updated_by\": \"user123\",\n" +
                "  \"updated_at\": 1672531199\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 调用方法
        DatasetResponse response = difyDatasetsApi.getDataset("3c90c3cc-0d44-4b50-8888-8dd25736052a");

        // 验证响应
        assertEquals("3c90c3cc-0d44-4b50-8888-8dd25736052a", response.getId());
        assertEquals("测试知识库", response.getName());
        assertEquals("这是一个测试知识库", response.getDescription());
        assertEquals(Integer.valueOf(3), response.getAppCount());
        assertEquals(Integer.valueOf(15), response.getDocumentCount());
    }

    @Test
    public void testDeleteDataset() {
        // 设置模拟响应 - 删除操作通常返回空响应
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(204)
                .addHeader("Content-Type", "application/json"));

        // 调用方法 - 删除操作无返回值，只要不抛异常即为成功
        difyDatasetsApi.deleteDataset("3c90c3cc-0d44-4b50-8888-8dd25736052a");
    }

    @Test
    public void testCreateDocumentByText() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"document\": {\n" +
                "    \"id\": \"doc123\",\n" +
                "    \"position\": 1,\n" +
                "    \"data_source_type\": \"upload_file\",\n" +
                "    \"name\": \"测试文档\",\n" +
                "    \"created_from\": \"api\",\n" +
                "    \"created_by\": \"user123\",\n" +
                "    \"created_at\": 1672531199\n" +
                "  },\n" +
                "  \"batch\": \"batch123\"\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 创建请求对象
        CreateDocumentByTextRequest request = CreateDocumentByTextRequest.builder()
                .name("测试文档")
                .text("这是测试文档的内容")
                .indexingTechnique("high_quality")
                .build();

        // 调用方法
        DocumentResponse response = difyDatasetsApi.createDocumentByText("dataset123", request);

        // 验证响应
        assertNotNull(response.getDocument());
        assertEquals("doc123", response.getDocument().getId());
        assertEquals("测试文档", response.getDocument().getName());
        assertEquals("batch123", response.getBatch());
    }

    @Test
    public void testUpdateDocumentByText() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"document\": {\n" +
                "    \"id\": \"doc123\",\n" +
                "    \"position\": 1,\n" +
                "    \"data_source_type\": \"upload_file\",\n" +
                "    \"name\": \"更新后的文档\",\n" +
                "    \"created_from\": \"api\",\n" +
                "    \"created_by\": \"user123\",\n" +
                "    \"created_at\": 1672531199,\n" +
                "    \"updated_at\": 1672617599\n" +
                "  },\n" +
                "  \"batch\": \"batch456\"\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 创建请求对象
        UpdateDocumentByTextRequest request = UpdateDocumentByTextRequest.builder()
                .name("更新后的文档")
                .text("这是更新后的文档内容")
                .build();

        // 调用方法
        DocumentResponse response = difyDatasetsApi.updateDocumentByText("dataset123", "doc123", request);

        // 验证响应
        assertNotNull(response.getDocument());
        assertEquals("doc123", response.getDocument().getId());
        assertEquals("更新后的文档", response.getDocument().getName());
        assertEquals("batch456", response.getBatch());
    }

    @Test
    public void testGetDocumentIndexingStatus() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"data\":[{\n" +
                "    \"id\": \"document123\",\n" +
                "    \"indexing_status\": \"indexing\",\n" +
                "    \"processing_started_at\": 1681623462.0,\n" +
                "    \"parsing_completed_at\": 1681623462.0,\n" +
                "    \"cleaning_completed_at\": 1681623462.0,\n" +
                "    \"splitting_completed_at\": 1681623462.0,\n" +
                "    \"completed_at\": null,\n" +
                "    \"paused_at\": null,\n" +
                "    \"error\": null,\n" +
                "    \"stopped_at\": null,\n" +
                "    \"completed_segments\": 24,\n" +
                "    \"total_segments\": 100\n" +
                "  }]\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 调用方法
        DocumentIndexingStatusResponse response = difyDatasetsApi.getDocumentIndexingStatus("dataset123", "batch123");

        // 验证响应
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getData().size() > 0);
        assertEquals("document123", response.getData().get(0).getId());
        assertEquals("indexing", response.getData().get(0).getIndexingStatus());
        assertEquals(Integer.valueOf(24), response.getData().get(0).getCompletedSegments());
        assertEquals(Integer.valueOf(100), response.getData().get(0).getTotalSegments());
    }

    @Test
    public void testDeleteDocument() {
        // 设置模拟响应 - 删除操作通常返回空响应
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(204)
                .addHeader("Content-Type", "application/json"));

        // 调用方法 - 删除操作无返回值，只要不抛异常即为成功
        difyDatasetsApi.deleteDocument("dataset123", "doc123");
    }

    @Test
    public void testListDocuments() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"id\": \"doc1\",\n" +
                "      \"position\": 1,\n" +
                "      \"data_source_type\": \"upload_file\",\n" +
                "      \"name\": \"文档1\",\n" +
                "      \"created_from\": \"api\",\n" +
                "      \"created_by\": \"user123\",\n" +
                "      \"created_at\": 1672531199\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": \"doc2\",\n" +
                "      \"position\": 2,\n" +
                "      \"data_source_type\": \"upload_file\",\n" +
                "      \"name\": \"文档2\",\n" +
                "      \"created_from\": \"api\",\n" +
                "      \"created_by\": \"user456\",\n" +
                "      \"created_at\": 1672617599\n" +
                "    }\n" +
                "  ],\n" +
                "  \"has_more\": false,\n" +
                "  \"limit\": 20,\n" +
                "  \"total\": 2,\n" +
                "  \"page\": 1\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 调用方法
        DocumentListResponse response = difyDatasetsApi.listDocuments("dataset123", null, 1, 20);

        // 验证响应
        assertNotNull(response.getData());
        assertEquals(2, response.getData().size());
        assertEquals("文档1", response.getData().get(0).getName());
        assertEquals("文档2", response.getData().get(1).getName());
        assertEquals(Integer.valueOf(2), response.getTotal());
    }

    @Test
    public void testListEmbeddingModels() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"data\": [\n" +
                "      {\n" +
                "          \"provider\": \"zhipuai\",\n" +
                "          \"label\": {\n" +
                "              \"zh_Hans\": \"智谱 AI\",\n" +
                "              \"en_US\": \"ZHIPU AI\"\n" +
                "          },\n" +
                "          \"icon_small\": {\n" +
                "              \"zh_Hans\": \"http://127.0.0.1:5001/console/api/workspaces/current/model-providers/zhipuai/icon_small/zh_Hans\",\n" +
                "              \"en_US\": \"http://127.0.0.1:5001/console/api/workspaces/current/model-providers/zhipuai/icon_small/en_US\"\n" +
                "          },\n" +
                "          \"icon_large\": {\n" +
                "              \"zh_Hans\": \"http://127.0.0.1:5001/console/api/workspaces/current/model-providers/zhipuai/icon_large/zh_Hans\",\n" +
                "              \"en_US\": \"http://127.0.0.1:5001/console/api/workspaces/current/model-providers/zhipuai/icon_large/en_US\"\n" +
                "          },\n" +
                "          \"status\": \"active\",\n" +
                "          \"models\": [\n" +
                "              {\n" +
                "                  \"model\": \"embedding-3\",\n" +
                "                  \"label\": {\n" +
                "                      \"zh_Hans\": \"embedding-3\",\n" +
                "                      \"en_US\": \"embedding-3\"\n" +
                "                  },\n" +
                "                  \"model_type\": \"text-embedding\",\n" +
                "                  \"features\": null,\n" +
                "                  \"fetch_from\": \"predefined-model\",\n" +
                "                  \"model_properties\": {\n" +
                "                      \"context_size\": 8192\n" +
                "                  },\n" +
                "                  \"deprecated\": false,\n" +
                "                  \"status\": \"active\",\n" +
                "                  \"load_balancing_enabled\": false\n" +
                "              },\n" +
                "              {\n" +
                "                  \"model\": \"embedding-2\",\n" +
                "                  \"label\": {\n" +
                "                      \"zh_Hans\": \"embedding-2\",\n" +
                "                      \"en_US\": \"embedding-2\"\n" +
                "                  },\n" +
                "                  \"model_type\": \"text-embedding\",\n" +
                "                  \"features\": null,\n" +
                "                  \"fetch_from\": \"predefined-model\",\n" +
                "                  \"model_properties\": {\n" +
                "                      \"context_size\": 8192\n" +
                "                  },\n" +
                "                  \"deprecated\": false,\n" +
                "                  \"status\": \"active\",\n" +
                "                  \"load_balancing_enabled\": false\n" +
                "              },\n" +
                "              {\n" +
                "                  \"model\": \"text_embedding\",\n" +
                "                  \"label\": {\n" +
                "                      \"zh_Hans\": \"text_embedding\",\n" +
                "                      \"en_US\": \"text_embedding\"\n" +
                "                  },\n" +
                "                  \"model_type\": \"text-embedding\",\n" +
                "                  \"features\": null,\n" +
                "                  \"fetch_from\": \"predefined-model\",\n" +
                "                  \"model_properties\": {\n" +
                "                      \"context_size\": 512\n" +
                "                  },\n" +
                "                  \"deprecated\": false,\n" +
                "                  \"status\": \"active\",\n" +
                "                  \"load_balancing_enabled\": false\n" +
                "              }\n" +
                "          ]\n" +
                "      }\n" +
                "  ]\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 调用方法
        EmbeddingModelListResponse response = difyDatasetsApi.listEmbeddingModels();

        // 验证响应
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertNotNull(response.getData().get(0).getModels());
        assertEquals(3, response.getData().get(0).getModels().size());
        assertEquals("embedding-3", response.getData().get(0).getModels().get(0).getModel());
    }

    @Test
    public void testCreateMetadata() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"id\": \"metadata123\",\n" +
                "  \"key\": \"category\",\n" +
                "  \"name\": \"分类\",\n" +
                "  \"type\": \"text\",\n" +
                "  \"created_by\": \"user123\",\n" +
                "  \"created_at\": 1672531199\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 创建请求对象
        CreateMetadataRequest request = CreateMetadataRequest.builder()
                .type("text")
                .name("分类")
                .build();

        // 调用方法
        MetadataResponse response = difyDatasetsApi.createMetadata("dataset123", request);

        // 验证响应
        assertEquals("metadata123", response.getId());
        assertEquals("分类", response.getName());
        assertEquals("text", response.getType());
    }

    @Test
    public void testUpdateMetadata() {
        // 设置模拟响应
        String mockResponseBody = "{\n" +
                "  \"id\": \"metadata123\",\n" +
                "  \"key\": \"category\",\n" +
                "  \"name\": \"更新后的分类\",\n" +
                "  \"type\": \"text\",\n" +
                "  \"created_by\": \"user123\",\n" +
                "  \"created_at\": 1672531199,\n" +
                "  \"updated_by\": \"user456\",\n" +
                "  \"updated_at\": 1672617599\n" +
                "}";
        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponseBody)
                .addHeader("Content-Type", "application/json"));

        // 创建请求对象
        UpdateMetadataRequest request = UpdateMetadataRequest.builder()
                .name("更新后的分类")
                .build();

        // 调用方法
        MetadataResponse response = difyDatasetsApi.updateMetadata("dataset123", "metadata123", request);

        // 验证响应
        assertEquals("metadata123", response.getId());
        assertEquals("更新后的分类", response.getName());
        assertEquals("text", response.getType());
    }

    @Test
    public void testDeleteMetadata() {
        // 设置模拟响应 - 删除操作通常返回空响应
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(204)
                .addHeader("Content-Type", "application/json"));

        // 调用方法 - 删除操作无返回值，只要不抛异常即为成功
        difyDatasetsApi.deleteMetadata("dataset123", "metadata123");
    }
} 