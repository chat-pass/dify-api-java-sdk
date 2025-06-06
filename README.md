# Dify API Java SDK

[![Maven Central](https://img.shields.io/maven-central/v/io.github.chat-pass/dify-api-java-sdk.svg)](https://search.maven.org/search?q=g:io.github.chat-pass%20AND%20a:dify-api-java-sdk)
[![License](https://img.shields.io/github/license/chat-pass/dify-api-java-sdk)](https://github.com/chat-pass/dify-api-java-sdk/blob/main/LICENSE)
[![Java](https://img.shields.io/badge/Java-8%2B-blue)](https://www.java.com)
[![codecov](https://codecov.io/gh/chat-pass/dify-api-java-sdk/branch/main/graph/badge.svg)](https://codecov.io/gh/chat-pass/dify-api-java-sdk)

[English](README_EN.md) | 简体中文 | [日本語](README_JP.md)

Dify API Java Client 是一个用于与 [Dify](https://dify.ai) 平台进行交互的 Java 客户端库。它提供了对 Dify 应用 API 和知识库 API 的完整支持，让 Java 开发者能够轻松地将 Dify 的生成式 AI 能力集成到自己的应用中。

## 功能特性

Dify API Java Client 提供以下核心功能：

### 1. 多种应用类型支持

- **对话型应用 (Chat)**: 通过 `DifyChatApi` 调用对话型应用，支持会话管理、消息反馈等功能
- **文本生成应用 (Completion)**: 通过 `DIfyCompletionApi` 调用文本生成型应用
- **工作流编排对话 (Chatflow)**: 通过 `DifyChayflowApi` 调用工作流编排对话型应用
- **工作流应用 (Workflow)**: 通过 `DifyWorkflowApi` 调用工作流应用
- **知识库管理 (Datasets)**: 通过 `DifyDatasetsApi` 管理知识库、文档和检索

### 2. 丰富的交互模式

- **阻塞模式**: 同步调用API，等待完整响应
- **流式模式**: 通过回调接收实时生成的内容，支持打字机效果
- **文件处理**: 支持文件上传、语音转文字、文字转语音等多媒体功能

### 3. 完整的会话管理

- 创建和管理会话
- 获取历史消息
- 会话重命名
- 消息反馈（点赞/点踩）
- 获取建议问题

### 4. 知识库全流程支持

- 创建和管理知识库
- 上传和管理文档
- 文档分段管理
- 语义检索


### 4.标注管理
- 创建标注
- 更新标注
- 删除标注
- 标注回复初始设置
- 查询标注回复初始设置任务状态


### 6. 灵活的配置选项

- 自定义连接超时
- 自定义读写超时
- http proxy支持

## 安装

### 系统要求

- Java 8 或更高版本
- Maven 3.x 以上

### Maven

```xml
<dependency>
    <groupId>io.github.chat-pass</groupId>
    <artifactId>dify-api-java-sdk</artifactId>
    <version>1.0.1.FINAL</version>
</dependency>
```

## 快速开始

### 基本使用示例

```java
// 环境配置
Map<String,String> envs = System.getenv();
DifyChatApi difyChatApi = DifyApiFactory.newInstance(envs.get("DIFY_BASE_URL"),envs.get("DIFY_API_KEY")).newDifyChatApi();

// 发送聊天消息
ChatMessageRequest request = new ChatMessageRequest();
request.setUser("test-user");
request.setQuery("你好，请介绍一下自己");
request.setResponseMode(ResponseMode.BLOCKING);

ChatMessageResponse response = difyChatApi.sendChatMessage(request);
System.out.println(response.getAnswer());
```

### 📚 完整示例文档

本SDK提供了丰富的功能示例，涵盖所有主要使用场景：

**[🔗 查看完整示例文档 →](EXAMPLES.md)**

示例文档包含：
- 🚀 **基础API示例** - 应用信息获取、参数配置
- 💬 **对话型应用** - 阻塞式和流式聊天、消息处理  
- ⚡ **工作流应用** - 执行、监控、日志管理
- 📊 **数据集管理** - 知识库创建、文档管理、语义检索
- 🎵 **音频处理** - 文本转语音、语音转文本
- 📁 **文件管理** - 文件上传、处理
- 🛠️ **最佳实践** - 错误处理、性能优化、并发处理

## API 参考

### 客户端类型

| 客户端类型          | 描述                           | 主要功能                     |
| ------------------- | ------------------------------ | ---------------------------- |
| `DifyBaseApi`       | Didy的通用基础API              | 获取应用信息，文件上传下载等 |
| `DifyChatApi`       | 对话型应用客户端               | 对话、会话管理、消息反馈     |
| `DifyCompletionApi` | 文本生成型应用客户端           | 文本生成、停止生成           |
| `DifyChatFlowApi`   | 对话工作流编排对话型应用客户端 | 工作流编排对话               |
| `DifyWorkflowApi`   | 工作流应用客户端               | 执行工作流、工作流管理       |
| `DifyDatasetsApi`   | 知识库客户端                   | 知识库管理、文档管理、检索   |

### 响应模式

| 模式     | 枚举值                   | 描述                       |
| -------- | ------------------------ | -------------------------- |
| 阻塞模式 | `ResponseMode.BLOCKING`  | 同步调用，等待完整响应     |
| 流式模式 | `ResponseMode.STREAMING` | 通过回调接收实时生成的内容 |

### 事件类型

| 事件类型                | 描述                         |
| ----------------------- | ---------------------------- |
| `MessageEvent`          | 消息事件，包含生成的文本片段 |
| `MessageEndEvent`       | 消息结束事件，包含完整消息ID |
| `MessageFileEvent`      | 文件消息事件，包含文件信息   |
| `TtsMessageEvent`       | 文字转语音事件               |
| `TtsMessageEndEvent`    | 文字转语音结束事件           |
| `MessageReplaceEvent`   | 消息替换事件                 |
| `AgentMessageEvent`     | Agent消息事件                |
| `AgentThoughtEvent`     | Agent思考事件                |
| `WorkflowStartedEvent`  | 工作流开始事件               |
| `NodeStartedEvent`      | 节点开始事件                 |
| `NodeFinishedEvent`     | 节点完成事件                 |
| `WorkflowFinishedEvent` | 工作流完成事件               |
| `ErrorEvent`            | 错误事件                     |
| `PingEvent`             | 心跳事件                     |

## 高级配置

### 自定义HTTP客户端

```java
// 创建自定义配置
OkHttpClient customClient = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .pingInterval(15, TimeUnit.SECONDS)
                .build();
        DIfyApiServiceGenerator.configureHttpClient(customClient);
```

### http proxy设置

```
DIfyApiServiceGenerator.setHttpProxy("proxy.example.com", 8080, "username", "password");
```

## 贡献

欢迎贡献代码、报告问题或提出改进建议。请通过 GitHub Issues 或 Pull Requests 参与项目开发。

## 许可证

本项目采用 [Apache License 2.0](LICENSE) 许可证。

## 相关链接

- [📚 完整示例文档](EXAMPLES.md) - SDK使用示例和最佳实践
- [Dify 官网](https://dify.ai)
- [Dify 文档](https://docs.dify.ai)
- [Dify GitHub](https://github.com/langgenius/dify)
