Here's the English version of the README for the Dify API Java SDK:

# Dify API Java SDK

[![Maven Central](https://img.shields.io/maven-central/v/io.github.chat-pass/dify-api-java-sdk.svg)](https://search.maven.org/search?q=g:io.github.chat-pass%20AND%20a:dify-api-java-sdk)
[![License](https://img.shields.io/github/license/chat-pass/dify-api-java-sdk)](https://github.com/chat-pass/dify-api-java-sdk/blob/main/LICENSE)
[![Java](https://img.shields.io/badge/Java-8%2B-blue)](https://www.java.com)

English | [简体中文](README.md)) | [日本語](README_JP.md)

The Dify API Java Client is a Java client library for interacting with the [Dify](https://dify.ai) platform. It provides full support for Dify's application API and knowledge base API, allowing Java developers to easily integrate Dify's generative AI capabilities into their applications.

## Features

The Dify API Java Client offers the following core features:

### 1. Support for Multiple Application Types

- **Conversational Applications (Chat)**: Use `DifyChatApi` to call conversational applications, supporting session management, message feedback, and more.
- **Text Generation Applications (Completion)**: Use `DIfyCompletionApi` to call text generation applications.
- **Workflow Orchestrated Conversations (Chatflow)**: Use `DifyChayflowApi` to call workflow orchestrated conversational applications.
- **Workflow Applications (Workflow)**: Use `DifyWorkflowApi` to call workflow applications.
- **Knowledge Base Management (Datasets)**: Use `DifyDatasetsApi` to manage knowledge bases, documents, and retrieval.

### 2. Rich Interaction Modes

- **Blocking Mode**: Synchronous API calls, waiting for a complete response.
- **Streaming Mode**: Receive real-time generated content via callbacks, supporting typewriter effects.
- **File Handling**: Support for file uploads, speech-to-text, text-to-speech, and other multimedia functions.

### 3. Comprehensive Session Management

- Create and manage sessions.
- Retrieve historical messages.
- Rename sessions.
- Message feedback (like/dislike).
- Get suggested questions.

### 4. Full Knowledge Base Support

- Create and manage knowledge bases.
- Upload and manage documents.
- Document segmentation management.
- Semantic retrieval.

### 5. Annotation Management

- Create annotations.
- Update annotations.
- Delete annotations.
- Initial setup for annotation replies.
- Query task status for initial setup of annotation replies.

### 6. Flexible Configuration Options

- Custom connection timeout.
- Custom read/write timeout.
- HTTP proxy support.

## Installation

### System Requirements

- Java 8 or higher.
- Maven 3.x or higher.

### Maven

```xml
<dependency>
    <groupId>io.github.chat-pass</groupId>
    <artifactId>dify-api-java-sdk</artifactId>
    <version>1.0.1.FINAL</version>
</dependency>
```

## API Reference

### Client Types

| Client Type          | Description                        | Main Features                  |
| -------------------- | ---------------------------------- | ------------------------------ |
| `DifyBaseApi`        | General base API for Dify          | Get application info, file upload/download, etc. |
| `DifyChatApi`        | Conversational application client  | Conversations, session management, message feedback |
| `DifyCompletionApi`  | Text generation application client | Text generation, stop generation |
| `DifyChatFlowApi`    | Workflow orchestrated conversation client | Workflow orchestrated conversations |
| `DifyWorkflowApi`    | Workflow application client        | Execute workflows, workflow management |
| `DifyDatasetsApi`    | Knowledge base client              | Knowledge base management, document management, retrieval |

### Response Modes

| Mode      | Enum Value                | Description                    |
| --------- | ------------------------- | ------------------------------ |
| Blocking  | `ResponseMode.BLOCKING`   | Synchronous call, waiting for a complete response |
| Streaming | `ResponseMode.STREAMING`  | Receive real-time generated content via callbacks |

### Event Types

| Event Type              | Description                      |
| ----------------------- | -------------------------------- |
| `MessageEvent`          | Message event, includes generated text fragments |
| `MessageEndEvent`       | Message end event, includes complete message ID |
| `MessageFileEvent`      | File message event, includes file information |
| `TtsMessageEvent`       | Text-to-speech event             |
| `TtsMessageEndEvent`    | Text-to-speech end event         |
| `MessageReplaceEvent`   | Message replace event            |
| `AgentMessageEvent`     | Agent message event              |
| `AgentThoughtEvent`     | Agent thought event              |
| `WorkflowStartedEvent`  | Workflow started event           |
| `NodeStartedEvent`      | Node started event               |
| `NodeFinishedEvent`     | Node finished event              |
| `WorkflowFinishedEvent` | Workflow finished event          |
| `ErrorEvent`            | Error event                      |
| `PingEvent`             | Ping event                       |

## Advanced Configuration

### Custom HTTP Client

```java
// Create custom configuration
OkHttpClient customClient = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .pingInterval(15, TimeUnit.SECONDS)
                .build();
DIfyApiServiceGenerator.configureHttpClient(customClient);
```

### HTTP Proxy Settings

```java
DIfyApiServiceGenerator.setHttpProxy("proxy.example.com", 8080, "username", "password");
```

## Contribution

Contributions, issue reports, and suggestions for improvements are welcome. Please participate in project development through GitHub Issues or Pull Requests.

## License

This project is licensed under the [Apache License 2.0](LICENSE).

## Related Links

- [Dify Official Website](https://dify.ai)
- [Dify Documentation](https://docs.dify.ai)
- [Dify GitHub](https://github.com/langgenius/dify)