# Dify API Java SDK 完整示例文档

[![Maven Central](https://img.shields.io/maven-central/v/io.github.chat-pass/dify-api-java-sdk.svg)](https://search.maven.org/search?q=g:io.github.chat-pass%20AND%20a:dify-api-java-sdk)
[![License](https://img.shields.io/github/license/chat-pass/dify-api-java-sdk)](https://github.com/chat-pass/dify-api-java-sdk/blob/main/LICENSE)
[![Java](https://img.shields.io/badge/Java-8%2B-blue)](https://www.java.com)

本文档提供了 Dify API Java SDK 的完整使用示例，涵盖了所有主要功能模块。通过这些示例，您可以快速了解如何在Java应用中集成Dify的强大AI能力。

## 目录

- [环境配置](#环境配置)
- [基础API示例](#基础api示例)
- [对话型应用示例](#对话型应用示例)  
- [工作流应用示例](#工作流应用示例)
- [数据集管理示例](#数据集管理示例)
- [音频处理示例](#音频处理示例)
- [文件管理示例](#文件管理示例)

## 环境配置

所有示例都需要先配置环境变量：

```bash
export DIFY_BASE_URL="https://api.dify.ai/v1"
export DIFY_API_KEY="your-api-key-here"
```
## 🚀 **完整应用示例**

### **企业级聊天应用**
```java
import io.github.chatpass.dify.config.DifyApiConfig;
import io.github.chatpass.dify.api.DifyChatApi;
import io.github.chatpass.dify.util.MetricsUtils;
import io.github.chatpass.dify.util.RetryUtils;

public class ChatService {
    private final DifyChatApi chatApi;
    
    public ChatService(String baseUrl, String apiKey) {
        // 企业级配置
        DifyApiConfig config = DifyApiConfig.builder(baseUrl, apiKey)
            .callTimeout(30)                    // 30秒超时
            .readTimeout(60)                    // 60秒读取超时
            .retryConfig(3, 1000)              // 3次重试
            .enableRetry(true)                 // 启用重试
            .enableLogging(true)               // 启用日志
            .build();
            
        this.chatApi = new DifyChatApi(config);
    }
    
    public ChatResponse sendMessage(String user, String message) {
        // 构建请求
        ChatMessageRequest request = new ChatMessageRequest();
        request.setUser(user);
        request.setQuery(message);
        
        // 带监控和重试的API调用
        try (MetricsUtils.Timer timer = new MetricsUtils.Timer("chatService.sendMessage")) {
            return RetryUtils.executeWithDefaultRetry(() -> {
                ChatMessageResponse response = chatApi.sendChatMessage(request);
                return new ChatResponse(response.getAnswer(), response.getMessageId());
            });
        } catch (Exception e) {
            timer.markError();
            throw new RuntimeException("Failed to send chat message", e);
        }
    }
    
    public void printPerformanceStats() {
        MetricsUtils.printMetrics();
    }
    
    // 内部响应类
    public static class ChatResponse {
        private final String answer;
        private final String messageId;
        
        public ChatResponse(String answer, String messageId) {
            this.answer = answer;
            this.messageId = messageId;
        }
        
        // getters...
    }
}
```

### **使用企业级聊天服务**
```java
public class Application {
    public static void main(String[] args) {
        // 初始化服务
        ChatService chatService = new ChatService(
            "https://api.dify.ai", 
            "your-api-key"
        );
        
        // 发送消息
        try {
            ChatResponse response = chatService.sendMessage("user123", "Hello AI!");
            System.out.println("AI回复: " + response.getAnswer());
            
            // 批量测试
            for (int i = 0; i < 10; i++) {
                chatService.sendMessage("user" + i, "Test message " + i);
            }
            
            // 查看性能统计
            chatService.printPerformanceStats();
            
        } catch (Exception e) {
            System.err.println("发送消息失败: " + e.getMessage());
        }
    }
}
```

## 📝 **最佳实践**

### **1. 配置管理**
```java
// ✅ 推荐：使用配置类
DifyApiConfig config = DifyApiConfig.builder(baseUrl, apiKey)
    .callTimeout(30)
    .enableRetry(true)
    .build();

// ⚠️ 可用：传统方式
DifyApiFactory factory = DifyApiFactory.newInstance(baseUrl, apiKey);
```

### **2. 错误处理**
```java
// ✅ 推荐：使用重试机制
try {
    return RetryUtils.executeWithDefaultRetry(() -> api.call());
} catch (Exception e) {
    log.error("API call failed after retries", e);
    throw new ServiceException("Service temporarily unavailable", e);
}
```

### **3. 性能监控**
```java
// ✅ 推荐：监控所有关键API调用
try (MetricsUtils.Timer timer = new MetricsUtils.Timer("criticalOperation")) {
    // 关键操作
    return performOperation();
} catch (Exception e) {
    timer.markError();
    throw e;
}
```

### **4. 资源管理**
```java
// ✅ 推荐：定期清理监控数据
@Scheduled(fixedRate = 3600000) // 每小时
public void cleanupMetrics() {
    MetricsUtils.printMetrics();  // 打印统计
    MetricsUtils.clearMetrics();  // 清空数据
}
```

## 基础API示例

### 1. 获取应用信息

获取当前应用的基本信息。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyBaseApi;
import io.github.chatpass.dify.data.response.AppMetaInfoResponse;

import java.util.Map;

public class DifyGetAppInfoExample {
    public static void main(String[] args) {
        Map<String,String> envs = System.getenv();
        DifyBaseApi difyBaseApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyBaseApi();
        AppMetaInfoResponse appMetaInfoResponse = difyBaseApi.getAppMetaInfo();
        System.out.println(appMetaInfoResponse);
    }
}
```

### 2. 获取应用元信息

获取应用的详细元数据信息。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyBaseApi;
import io.github.chatpass.dify.data.response.AppMetaInfoResponse;
import io.github.chatpass.dify.util.JSONUtils;

import java.util.Map;

public class DifyGetAppMetaInfoExample {
    public static void main(String[] args) {
        Map<String,String> envs = System.getenv();
        DifyBaseApi difyBaseApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyBaseApi();
        AppMetaInfoResponse appInfoResponse = difyBaseApi.getAppMetaInfo();
        System.out.println(JSONUtils.toJson(appInfoResponse));
    }
}
```

### 3. 获取应用参数

获取应用的配置参数。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyBaseApi;
import io.github.chatpass.dify.data.response.AppParametersResponse;
import io.github.chatpass.dify.util.JSONUtils;

import java.util.Map;

public class DifyGetAppParameters {
    public static void main(String[] args) {
        Map<String,String> envs = System.getenv();
        DifyBaseApi difyBaseApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyBaseApi();
        AppParametersResponse appParameters = difyBaseApi.getAppParameters();
        System.out.println(JSONUtils.toJson(appParameters));
    }
}
```

### 4. 获取Web应用信息

获取Web应用的相关信息。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyBaseApi;
import io.github.chatpass.dify.data.response.WebAppResponse;
import io.github.chatpass.dify.util.JSONUtils;

import java.util.Map;

public class DIfyGetWebAppInfo {
    public static void main(String[] args) {
        Map<String,String> envs = System.getenv();
        DifyBaseApi difyBaseApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyBaseApi();
        WebAppResponse webAppInfo = difyBaseApi.getWebAppInfo();
        System.out.println(JSONUtils.toJson(webAppInfo));
    }
}
```

## 对话型应用示例

### 1. 发送聊天消息（阻塞模式）

同步发送聊天消息并等待完整响应。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyChatApi;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.request.ChatMessageRequest;
import io.github.chatpass.dify.data.response.ChatMessageResponse;
import io.github.chatpass.dify.util.JSONUtils;

import java.util.Map;

public class DifySendChatMessageExample {
    public static void main(String[] args) {
        Map<String,String> envs = System.getenv();
        DifyChatApi difyChatApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyChatApi();
        String userId = "test-user-" + System.currentTimeMillis();
        ChatMessageRequest request = new ChatMessageRequest();
        request.setUser(userId);
        request.setQuery("你好，请介绍一下自己");
        request.setResponseMode(ResponseMode.BLOCKING);
        ChatMessageResponse response = difyChatApi.sendChatMessage(request);
        System.out.println(JSONUtils.toJson(response));
    }
}
```

### 2. 发送聊天消息（流式模式）

通过流式回调实时接收AI生成的内容，支持打字机效果。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyChatApi;
import io.github.chatpass.dify.api.callback.ChatStreamCallback;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.event.*;
import io.github.chatpass.dify.data.request.ChatMessageRequest;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class DifySendChatStreamMessageExample {

    public static void main(String[] args) throws InterruptedException {
        Map<String,String> envs = System.getenv();
        DifyChatApi difyChatApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyChatApi();
        String userId = "test-user-" + System.currentTimeMillis();
        ChatMessageRequest request = new ChatMessageRequest();
        request.setUser(userId);
        request.setQuery("你好，请介绍一下自己");
        request.setResponseMode(ResponseMode.STREAMING);
        CountDownLatch latch = new CountDownLatch(1);
        StringBuilder responseBuilder = new StringBuilder();
        AtomicReference<String> messageId = new AtomicReference<>();
        
        difyChatApi.sendChatMessageStream(request, new ChatStreamCallback() {
            @Override
            public void onMessage(MessageEvent event) {
                System.out.println("receive chunk: " + event.getAnswer());
                responseBuilder.append(event.getAnswer());
            }

            @Override
            public void onMessageEnd(MessageEndEvent event) {
                System.out.println("message end messageID: " + event.getId());
                messageId.set(event.getId());
                latch.countDown();
            }

            @Override
            public void onMessageFile(MessageFileEvent event) {
                System.out.println("receive file: " + event);
            }

            @Override
            public void onTTSMessage(TtsMessageEvent event) {
                System.out.println("receive tts: " + event);
            }

            @Override
            public void onTTSMessageEnd(TtsMessageEndEvent event) {
                System.out.println("TTS end: " + event);
            }

            @Override
            public void onMessageReplace(MessageReplaceEvent event) {
                System.out.println("message replace: " + event);
            }

            @Override
            public void onAgentMessage(AgentMessageEvent event) {
                System.out.println("Agent message: " + event);
            }

            @Override
            public void onAgentThought(AgentThoughtEvent event) {
                System.out.println("Agent thought: " + event);
            }

            @Override
            public void onError(ErrorEvent event) {
                System.err.println("error: " + event.getMessage());
                latch.countDown();
            }

            @Override
            public void onException(Throwable throwable) {
                System.err.println("异常: " + throwable.getMessage());
                latch.countDown();
            }

            @Override
            public void onPing(PingEvent event) {
                System.out.println("心跳: " + event);
            }
        });

        boolean completed = latch.await(100, TimeUnit.SECONDS);
        System.out.println(responseBuilder.toString());
    }
}
```

## 工作流应用示例

### 1. 工作流执行（阻塞模式）

执行工作流并获取状态信息。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyWorkflowApi;
import io.github.chatpass.dify.data.enums.ResponseMode;
import io.github.chatpass.dify.data.request.SimpleUserRequest;
import io.github.chatpass.dify.data.request.WorkflowRunRequest;
import io.github.chatpass.dify.data.response.WorkflowLogListResponse;
import io.github.chatpass.dify.data.response.WorkflowRunResponse;
import io.github.chatpass.dify.data.response.WorkflowRunStatusResponse;
import io.github.chatpass.dify.data.response.WorkflowStopResponse;
import io.github.chatpass.dify.exception.DifyApiException;

import java.util.HashMap;
import java.util.Map;

public class DifyWorkflowApiExample {

    private static String workflowRunId;
    private static String taskId;
    private static DifyWorkflowApi difyWorkflowApi;

    public static void main(String[] args) {
        final Map<String, String> envs = System.getenv();
        difyWorkflowApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"), envs.get("DIFY_API_KEY")).newDifyWorkflowApi();

        try {
            runWorkflowExample();
            getWorkflowRunStatusExample();
            stopWorkflowExample();
            getworkflowlogsExample();
            getWorkflowLogsWithKeywordExample();
            getWorkflowLogsWithStatusExample();
            getWorkflowLogsWithAllParamsExample();
        } catch (DifyApiException e) {
            e.printStackTrace();
        }
    }

    private static void runWorkflowExample() {
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("query", "请帮我翻译这句话：Hello World");

        WorkflowRunRequest request = WorkflowRunRequest.builder()
            .inputs(inputs)
            .responseMode(ResponseMode.BLOCKING)
            .user("test_user_" + System.currentTimeMillis())
            .build();

        WorkflowRunResponse response = difyWorkflowApi.runWorkflow(request);
        System.out.println("执行workflow响应: " + response);

        // 保存workflowRunId和taskId用于后续测试
        workflowRunId = response.getWorkflowRunId();
        taskId = response.getTaskId();
    }

    private static void getWorkflowRunStatusExample() {
        WorkflowRunStatusResponse response = difyWorkflowApi.getWorkflowRunStatus(workflowRunId);
        System.out.println("获取workflow执行状态响应: " + response);
    }

    private static void stopWorkflowExample() {
        SimpleUserRequest request = SimpleUserRequest.builder()
            .user("test_user_" + System.currentTimeMillis())
            .build();

        WorkflowStopResponse response = difyWorkflowApi.stopWorkflow(taskId, request);
        System.out.println("停止workflow响应: " + response);
    }

    private static void getworkflowlogsExample() {
        WorkflowLogListResponse response = difyWorkflowApi.getWorkflowLogs(null, null, 1, 10);
        System.out.println("获取workflow日志列表响应: " + response);
    }

    private static void getWorkflowLogsWithKeywordExample() {
        WorkflowLogListResponse response = difyWorkflowApi.getWorkflowLogs("测试", null, 1, 5);
        System.out.println("带关键词的workflow日志列表响应: " + response);
    }

    private static void getWorkflowLogsWithStatusExample() {
        WorkflowLogListResponse response = difyWorkflowApi.getWorkflowLogs(null, "succeeded", 1, 10);
        System.out.println("带状态筛选的workflow日志列表响应: " + response);
    }

    private static void getWorkflowLogsWithAllParamsExample() {
        WorkflowLogListResponse response = difyWorkflowApi.getWorkflowLogs("翻译", "succeeded", 1, 5);
        System.out.println("带所有参数的workflow日志列表响应: " + response);
    }
}
```

### 2. 工作流执行（流式模式）

实时监控工作流执行过程。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyWorkflowApi;
import io.github.chatpass.dify.api.callback.WorkflowStreamCallback;
import io.github.chatpass.dify.data.event.*;
import io.github.chatpass.dify.data.request.WorkflowRunRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DifyWorkflowRunStreamExample {
    private static DifyWorkflowApi difyWorkflowApi;

    public static void main(String[] args) {
        final Map<String, String> envs = System.getenv();
        difyWorkflowApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyWorkflowApi();

        runWorkflowStreamExample();
    }

    private static void runWorkflowStreamExample() {
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("query", "请帮我写一首关于春天的诗");
        
        WorkflowRunRequest request = WorkflowRunRequest.builder()
                .inputs(inputs)
                .user("test_user_stream_" + System.currentTimeMillis())
                .build();
        
        CountDownLatch latch = new CountDownLatch(1);
        
        WorkflowStreamCallback callback = new WorkflowStreamCallback() {
            @Override
            public void onWorkflowStarted(WorkflowStartedEvent event) {
                System.out.println("工作流开始: " + event);
            }
            
            @Override
            public void onNodeStarted(NodeStartedEvent event) {
                System.out.println("节点开始: " + event);
            }
            
            @Override
            public void onNodeFinished(NodeFinishedEvent event) {
                System.out.println("节点完成: " + event);
            }
            
            @Override
            public void onWorkflowFinished(WorkflowFinishedEvent event) {
                System.out.println("工作流完成: " + event);
                latch.countDown();
            }
            
            @Override
            public void onWorkflowTextChunk(WorkflowTextChunkEvent event) {
                System.out.println("工作流文本块: " + event);
            }
            
            @Override
            public void onTtsMessage(TtsMessageEvent event) {
                System.out.println("TTS消息: " + event);
            }
            
            @Override
            public void onTtsMessageEnd(TtsMessageEndEvent event) {
                System.out.println("TTS消息结束: " + event);
            }
            
            @Override
            public void onException(Throwable e) {
                System.err.println("流式响应异常: " + e.getMessage());
                e.printStackTrace();
                latch.countDown();
            }
        };
        
        difyWorkflowApi.runWorkflowStream(request, callback);

        try {
            latch.await(120, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

## 数据集管理示例

### 1. 数据集文本管理完整示例

涵盖数据集创建、文档管理、分段处理、元数据管理等全流程操作。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyDatasetsApi;
import io.github.chatpass.dify.data.request.datasets.*;
import io.github.chatpass.dify.data.response.datasets.*;
import io.github.chatpass.dify.exception.DifyApiException;

import java.util.Map;

public class DifyDatasetsApiExample {

    private static DifyDatasetsApi difyDatasetsApi;
    private static String datasetId;
    private static String documentId;
    private static String segmentId;
    private static String childChunkId;
    private static String metadataId;

    public static void main(String[] args) {
        final Map<String, String> envs = System.getenv();
        difyDatasetsApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"), envs.get("DIFY_API_KEY")).newDifyDatasetsApi();
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
            toggleBuiltInMetadataExample();
            updateDocumentMetadataExample();
            listDatasetMetadataExample();
        } catch (DifyApiException e) {
            e.printStackTrace();
        } finally {
            deleteMetadata();
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

    // 省略其他方法的详细实现...
    // 完整代码请参考源码中的 DifyDatasetsApiExample.java
}
```

### 2. 数据集文件管理示例

支持文件上传、更新和检索功能。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyDatasetsApi;
import io.github.chatpass.dify.data.request.datasets.*;
import io.github.chatpass.dify.data.response.datasets.*;
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
        difyDatasetsApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"), envs.get("DIFY_API_KEY")).newDifyDatasetsApi();
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

    // 省略其他方法实现...
}
```

## 音频处理示例

### 1. 文本转音频

将文本转换为语音文件。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyChatApi;
import io.github.chatpass.dify.data.request.TextToAudioRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class DifyTextToAudioExample {
    public static void main(String[] args) throws IOException {
        Map<String,String> envs = System.getenv();
        DifyChatApi difyChatApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyChatApi();
        String userId = "test-user-" + System.currentTimeMillis();
        TextToAudioRequest request = new TextToAudioRequest();
        request.setText("今天是一个好天气");
        request.setUser(userId);
        byte[] result = difyChatApi.textToAudio(request);
        if(result != null){
            Files.write(Paths.get("output.wav"), result);
        }
    }
}
```

### 2. 音频转文本

将音频文件转换为文本。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyChatApi;
import io.github.chatpass.dify.data.response.AudioToTextResponse;
import io.github.chatpass.dify.util.JSONUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class DIfyAudioToTextExample {
    public static void main(String[] args) throws IOException {
        Map<String,String> envs = System.getenv();
        DifyChatApi difyChatApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyChatApi();
        String userId = "test-user-" + System.currentTimeMillis();
        AudioToTextResponse response = difyChatApi.audioToText(new File("output.wav"),userId);
        System.out.println(JSONUtils.toJson(response));
    }
}
```

## 文件管理示例

### 文件上传

支持各种格式的文件上传。

```java
package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyBaseApi;
import io.github.chatpass.dify.data.request.UploadFileRequest;
import io.github.chatpass.dify.data.response.UploadFileResponse;
import io.github.chatpass.dify.util.JSONUtils;

import java.io.File;
import java.util.Map;

public class DifyUploadFileExample {
    public static void main(String[] args) {
        Map<String,String> envs = System.getenv();
        DifyBaseApi difyBaseApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyBaseApi();
        String userId = "test-user-" + System.currentTimeMillis();
        UploadFileRequest request = new UploadFileRequest(userId);
        File file = new File("D:\\test.txt");
        UploadFileResponse response = difyBaseApi.uploadFile(request,file);
        System.out.println(JSONUtils.toJson(response));
    }
}
```

## 最佳实践

### 1. 错误处理

```java
try {
    ChatMessageResponse response = difyChatApi.sendChatMessage(request);
} catch (DifyApiException e) {
    System.err.println("API错误: " + e.getMessage());
    System.err.println("错误代码: " + e.getError().getCode());
} catch (Exception e) {
    System.err.println("系统错误: " + e.getMessage());
}
```

### 2. 流式处理最佳实践

```java
CountDownLatch latch = new CountDownLatch(1);
StringBuilder responseBuilder = new StringBuilder();

ChatStreamCallback callback = new ChatStreamCallback() {
    @Override
    public void onMessage(MessageEvent event) {
        System.out.print(event.getAnswer());
        responseBuilder.append(event.getAnswer());
    }
    
    @Override
    public void onMessageEnd(MessageEndEvent event) {
        System.out.println("\n消息ID: " + event.getId());
        latch.countDown();
    }
    
    @Override
    public void onError(ErrorEvent event) {
        System.err.println("流式处理错误: " + event.getMessage());
        latch.countDown();
    }
};

difyChatApi.sendChatMessageStream(request, callback);
latch.await(30, TimeUnit.SECONDS);
```

### 3. 自定义HTTP客户端

```java
OkHttpClient customClient = new OkHttpClient.Builder()
    .callTimeout(30, TimeUnit.SECONDS)
    .connectTimeout(10, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .pingInterval(15, TimeUnit.SECONDS)
    .build();

DIfyApiServiceGenerator.configureHttpClient(customClient);
```

### 4. 代理设置

```java
DIfyApiServiceGenerator.setHttpProxy("proxy.example.com", 8080, "username", "password");
```

### 5. 资源清理

```java
// 对于需要清理的资源，建议使用try-finally模式
String datasetId = null;
String documentId = null;

try {
    // 创建和使用资源
    DatasetResponse dataset = difyDatasetsApi.createDataset(createRequest);
    datasetId = dataset.getId();
    
    DocumentResponse document = difyDatasetsApi.createDocumentByText(datasetId, docRequest);
    documentId = document.getDocument().getId();
    
    // 使用资源进行操作
    
} finally {
    // 清理资源
    if (documentId != null) {
        difyDatasetsApi.deleteDocument(datasetId, documentId);
    }
    if (datasetId != null) {
        difyDatasetsApi.deleteDataset(datasetId);
    }
}
```

## 常见问题

### Q: 如何处理大文件上传？
A: 对于大文件上传，建议增加HTTP客户端的超时时间：

```java
OkHttpClient client = new OkHttpClient.Builder()
    .writeTimeout(5, TimeUnit.MINUTES)
    .readTimeout(5, TimeUnit.MINUTES)
    .build();
DIfyApiServiceGenerator.configureHttpClient(client);
```

### Q: 流式响应如何处理异常？
A: 在流式回调中实现异常处理方法：

```java
@Override
public void onException(Throwable throwable) {
    System.err.println("流式处理异常: " + throwable.getMessage());
    latch.countDown();
}
```

### Q: 如何优化数据集检索性能？
A: 合理选择检索模式和参数：

```java
RetrievalModel retrievalModel = RetrievalModel.builder()
    .searchMethod("hybrid_search")  // 混合检索通常效果更好
    .rerankingEnable(true)         // 启用重排序提高准确性
    .topK(5)                       // 根据需要调整返回数量
    .scoreThresholdEnabled(true)   // 启用分数阈值过滤
    .scoreThreshold(0.5f)          // 设置合适的分数阈值
    .build();
```

### Q: 如何处理并发请求？
A: 建议使用线程池来管理并发请求：

```java
ExecutorService executor = Executors.newFixedThreadPool(10);

for (int i = 0; i < 100; i++) {
    final int requestId = i;
    executor.submit(() -> {
        try {
            ChatMessageRequest request = new ChatMessageRequest();
            request.setUser("user-" + requestId);
            request.setQuery("请求 " + requestId);
            
            ChatMessageResponse response = difyChatApi.sendChatMessage(request);
            System.out.println("请求 " + requestId + " 完成");
        } catch (Exception e) {
            System.err.println("请求 " + requestId + " 失败: " + e.getMessage());
        }
    });
}

executor.shutdown();
```

### Q: 如何实现重试机制？
A: 可以实现简单的重试逻辑：

```java
public ChatMessageResponse sendMessageWithRetry(ChatMessageRequest request, int maxRetries) {
    int attempts = 0;
    while (attempts < maxRetries) {
        try {
            return difyChatApi.sendChatMessage(request);
        } catch (DifyApiException e) {
            attempts++;
            if (attempts >= maxRetries) {
                throw e;
            }
            try {
                Thread.sleep(1000 * attempts); // 指数退避
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(ie);
            }
        }
    }
    throw new RuntimeException("超过最大重试次数");
}
```

## 总结

本文档提供了 Dify API Java SDK 的完整使用示例，涵盖了从基础功能到高级特性的所有主要用法。通过这些示例，您可以：

1. **快速上手** - 使用基础API获取应用信息
2. **实现对话** - 支持阻塞和流式两种模式  
3. **管理工作流** - 执行和监控工作流状态
4. **操作数据集** - 完整的知识库管理功能
5. **处理多媒体** - 音频和文件处理能力
6. **优化性能** - 连接池、缓存、并发处理
7. **错误处理** - 完善的异常处理机制

### 开发建议

1. **环境隔离** - 开发、测试、生产环境使用不同的API密钥
2. **日志记录** - 合理配置日志级别，便于问题排查
3. **监控告警** - 监控API调用频率、成功率、响应时间
4. **版本管理** - 定期更新SDK版本，获取最新功能和修复
5. **安全性** - 妥善保管API密钥，避免硬编码到代码中

### 技术支持

如果您在使用过程中遇到问题，可以通过以下方式获取帮助：

- [GitHub Issues](https://github.com/chat-pass/dify-api-java-sdk/issues) - 报告Bug或提出功能建议
- [Dify官方文档](https://docs.dify.ai) - 查看最新的API文档
- [社区论坛](https://github.com/langgenius/dify/discussions) - 与其他开发者交流

### 相关资源

- [Dify 官网](https://dify.ai)
- [Dify 开源项目](https://github.com/langgenius/dify)
- [API参考文档](https://docs.dify.ai/v/zh-hans/guides/application-orchestrate/based-on-backend-apis)
- [Java SDK源码](https://github.com/chat-pass/dify-api-java-sdk)
