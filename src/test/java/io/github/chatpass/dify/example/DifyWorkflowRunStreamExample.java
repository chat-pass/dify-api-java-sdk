package io.github.chatpass.dify.example;

import io.github.chatpass.dify.DifyApiFactory;
import io.github.chatpass.dify.api.DifyWorkflowApi;
import io.github.chatpass.dify.api.callback.WorkflowStreamCallback;
import io.github.chatpass.dify.data.event.NodeFinishedEvent;
import io.github.chatpass.dify.data.event.NodeStartedEvent;
import io.github.chatpass.dify.data.event.TtsMessageEndEvent;
import io.github.chatpass.dify.data.event.TtsMessageEvent;
import io.github.chatpass.dify.data.event.WorkflowFinishedEvent;
import io.github.chatpass.dify.data.event.WorkflowStartedEvent;
import io.github.chatpass.dify.data.event.WorkflowTextChunkEvent;
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
