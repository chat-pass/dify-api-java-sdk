

以下は、Dify API Java SDK の README を日本語に翻訳したものです：

# Dify API Java SDK

[![Maven Central](https://img.shields.io/maven-central/v/io.github.chat-pass/dify-api-java-sdk.svg)](https://search.maven.org/search?q=g:io.github.chat-pass%20AND%20a:dify-api-java-sdk)
[![License](https://img.shields.io/github/license/chat-pass/dify-api-java-sdk)](https://github.com/chat-pass/dify-api-java-sdk/blob/main/LICENSE)
[![Java](https://img.shields.io/badge/Java-8%2B-blue)](https://www.java.com)

[English](README_EN.md) | [简体中文](README_EN.md) | 日本語

Dify API Java クライアントは、[Dify](https://dify.ai) プラットフォームと対話するための Java クライアントライブラリです。Dify のアプリケーション API とナレッジベース API を完全にサポートし、Java 開発者が Dify の生成 AI 機能を自分のアプリケーションに簡単に統合できるようにします。

## 機能

Dify API Java クライアントは、以下の主要な機能を提供します：

### 1. 複数のアプリケーションタイプのサポート

- **会話型アプリケーション (Chat)**: `DifyChatApi` を使用して会話型アプリケーションを呼び出し、セッション管理、メッセージフィードバックなどの機能をサポートします。
- **テキスト生成アプリケーション (Completion)**: `DIfyCompletionApi` を使用してテキスト生成アプリケーションを呼び出します。
- **ワークフローオーケストレーション会話 (Chatflow)**: `DifyChayflowApi` を使用してワークフローオーケストレーション会話型アプリケーションを呼び出します。
- **ワークフローアプリケーション (Workflow)**: `DifyWorkflowApi` を使用してワークフローアプリケーションを呼び出します。
- **ナレッジベース管理 (Datasets)**: `DifyDatasetsApi` を使用してナレッジベース、ドキュメント、検索を管理します。

### 2. 豊富なインタラクションモード

- **ブロッキングモード**: 同期 API 呼び出し、完全な応答を待ちます。
- **ストリーミングモード**: コールバックを通じてリアルタイムで生成されたコンテンツを受信し、タイプライター効果をサポートします。
- **ファイル処理**: ファイルのアップロード、音声からテキストへの変換、テキストから音声への変換などのマルチメディア機能をサポートします。

### 3. 完全なセッション管理

- セッションの作成と管理。
- 履歴メッセージの取得。
- セッションの名前変更。
- メッセージフィードバック（いいね/よくない）。
- 推奨質問の取得。

### 4. ナレッジベースの完全サポート

- ナレッジベースの作成と管理。
- ドキュメントのアップロードと管理。
- ドキュメントのセグメント管理。
- セマンティック検索。

### 5. アノテーション管理

- アノテーションの作成。
- アノテーションの更新。
- アノテーションの削除。
- アノテーション返信の初期設定。
- アノテーション返信の初期設定タスクの状態を照会。

### 6. 柔軟な設定オプション

- カスタム接続タイムアウト。
- カスタム読み書きタイムアウト。
- HTTP プロキシのサポート。

## インストール

### システム要件

- Java 8 以上。
- Maven 3.x 以上。

### Maven

```language=xml
<dependency>
    <groupId>io.github.chat-pass</groupId>
    <artifactId>dify-api-java-sdk</artifactId>
    <version>1.0.1.FINAL</version>
</dependency>
```

## API リファレンス

### クライアントタイプ

| クライアントタイプ      | 説明                           | 主な機能                     |
| ------------------- | ------------------------------ | ---------------------------- |
| `DifyBaseApi`       | Dify の一般的な基本 API        | アプリケーション情報の取得、ファイルのアップロード/ダウンロードなど |
| `DifyChatApi`       | 会話型アプリケーションクライアント | 会話、セッション管理、メッセージフィードバック     |
| `DifyCompletionApi` | テキスト生成アプリケーションクライアント | テキスト生成、生成の停止           |
| `DifyChatFlowApi`   | ワークフローオーケストレーション会話型アプリケーションクライアント | ワークフローオーケストレーション会話               |
| `DifyWorkflowApi`   | ワークフローアプリケーションクライアント | ワークフローの実行、ワークフロー管理       |
| `DifyDatasetsApi`   | ナレッジベースクライアント       | ナレッジベース管理、ドキュメント管理、検索   |

### 応答モード

| モード     | 列挙値                   | 説明                       |
| -------- | ------------------------ | -------------------------- |
| ブロッキングモード | `ResponseMode.BLOCKING`  | 同期呼び出し、完全な応答を待ちます     |
| ストリーミングモード | `ResponseMode.STREAMING` | コールバックを通じてリアルタイムで生成されたコンテンツを受信 |

### イベントタイプ

| イベントタイプ                | 説明                         |
| ----------------------- | ---------------------------- |
| `MessageEvent`          | メッセージイベント、生成されたテキストフラグメントを含む |
| `MessageEndEvent`       | メッセージ終了イベント、完全なメッセージIDを含む |
| `MessageFileEvent`      | ファイルメッセージイベント、ファイル情報を含む   |
| `TtsMessageEvent`       | テキストから音声への変換イベント               |
| `TtsMessageEndEvent`    | テキストから音声への変換終了イベント           |
| `MessageReplaceEvent`   | メッセージ置換イベント                 |
| `AgentMessageEvent`     | エージェントメッセージイベント                |
| `AgentThoughtEvent`     | エージェント思考イベント                |
| `WorkflowStartedEvent`  | ワークフロー開始イベント               |
| `NodeStartedEvent`      | ノード開始イベント                 |
| `NodeFinishedEvent`     | ノード終了イベント                 |
| `WorkflowFinishedEvent` | ワークフロー終了イベント               |
| `ErrorEvent`            | エラーイベント                     |
| `PingEvent`             | ピングイベント                     |

## 高度な設定

### カスタムHTTPクライアント

```language=java
// カスタム設定を作成
OkHttpClient customClient = new OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .pingInterval(15, TimeUnit.SECONDS)
                .build();
DIfyApiServiceGenerator.configureHttpClient(customClient);
```

### HTTPプロキシ設定

```language=java
DIfyApiServiceGenerator.setHttpProxy("proxy.example.com", 8080, "username", "password");
```

## 貢献

コードの貢献、問題の報告、改善提案を歓迎します。GitHub Issues または Pull Requests を通じてプロジェクト開発に参加してください。

## ライセンス

このプロジェクトは [Apache License 2.0](LICENSE) ライセンスの下でライセンスされています。

## 関連リンク

- [Dify 公式サイト](https://dify.ai)
- [Dify ドキュメント](https://docs.dify.ai)
- [Dify GitHub](https://github.com/langgenius/dify)
