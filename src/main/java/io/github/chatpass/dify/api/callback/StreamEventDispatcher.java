package io.github.chatpass.dify.api.callback;

import io.github.chatpass.dify.data.enums.StreamEventType;
import io.github.chatpass.dify.data.event.*;
import io.github.chatpass.dify.util.JSONUtils;
import io.github.chatpass.dify.data.event.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Slf4j
public class StreamEventDispatcher {

    private static final Map<StreamEventType, BiConsumer<ChatflowStreamCallback, String>> chatFlowEventHandlers = new HashMap<>();
    private static final Map<StreamEventType, BiConsumer<ChatStreamCallback, String>> chatEventHandlers = new HashMap<>();
    private static final Map<StreamEventType, BiConsumer<CompletionStreamCallback, String>> completionEventHandlers = new HashMap<>();
    private static final Map<StreamEventType, BiConsumer<WorkflowStreamCallback, String>> workflowEventHandlers = new HashMap<>();

    static {
        // Initialize chat flow event handlers
        chatFlowEventHandlers.put(StreamEventType.MESSAGE, (callback, data) -> handleEvent(callback, data, MessageEvent.class, ChatflowStreamCallback::onMessage));
        chatFlowEventHandlers.put(StreamEventType.MESSAGE_END, (callback, data) -> handleEvent(callback, data, MessageEndEvent.class, ChatflowStreamCallback::onMessageEnd));
        chatFlowEventHandlers.put(StreamEventType.MESSAGE_FILE, (callback, data) -> handleEvent(callback, data, MessageFileEvent.class, ChatflowStreamCallback::onMessageFile));
        chatFlowEventHandlers.put(StreamEventType.TTS_MESSAGE, (callback, data) -> handleEvent(callback, data, TtsMessageEvent.class, ChatflowStreamCallback::onTTSMessage));
        chatFlowEventHandlers.put(StreamEventType.TTS_MESSAGE_END, (callback, data) -> handleEvent(callback, data, TtsMessageEndEvent.class, ChatflowStreamCallback::onTTSMessageEnd));
        chatFlowEventHandlers.put(StreamEventType.MESSAGE_REPLACE, (callback, data) -> handleEvent(callback, data, MessageReplaceEvent.class, ChatflowStreamCallback::onMessageReplace));
        chatFlowEventHandlers.put(StreamEventType.AGENT_MESSAGE, (callback, data) -> handleEvent(callback, data, AgentMessageEvent.class, ChatflowStreamCallback::onAgentMessage));
        chatFlowEventHandlers.put(StreamEventType.AGENT_THOUGHT, (callback, data) -> handleEvent(callback, data, AgentThoughtEvent.class, ChatflowStreamCallback::onAgentThought));
        chatFlowEventHandlers.put(StreamEventType.WORKFLOW_STARTED, (callback, data) -> handleEvent(callback, data, WorkflowStartedEvent.class, ChatflowStreamCallback::onWorkflowStarted));
        chatFlowEventHandlers.put(StreamEventType.NODE_STARTED, (callback, data) -> handleEvent(callback, data, NodeStartedEvent.class, ChatflowStreamCallback::onNodeStarted));
        chatFlowEventHandlers.put(StreamEventType.NODE_FINISHED, (callback, data) -> handleEvent(callback, data, NodeFinishedEvent.class, ChatflowStreamCallback::onNodeFinished));
        chatFlowEventHandlers.put(StreamEventType.WORKFLOW_FINISHED, (callback, data) -> handleEvent(callback, data, WorkflowFinishedEvent.class, ChatflowStreamCallback::onWorkflowFinished));
        chatFlowEventHandlers.put(StreamEventType.ERROR, (callback, data) -> handleEvent(callback, data, ErrorEvent.class, ChatflowStreamCallback::onError));
        chatFlowEventHandlers.put(StreamEventType.PING, (callback, data) -> handleEvent(callback, data, PingEvent.class, ChatflowStreamCallback::onPing));

        // Initialize chat event handlers
        chatEventHandlers.put(StreamEventType.MESSAGE, (callback, data) -> handleEvent(callback, data, MessageEvent.class, ChatStreamCallback::onMessage));
        chatEventHandlers.put(StreamEventType.MESSAGE_END, (callback, data) -> handleEvent(callback, data, MessageEndEvent.class, ChatStreamCallback::onMessageEnd));
        chatEventHandlers.put(StreamEventType.MESSAGE_FILE, (callback, data) -> handleEvent(callback, data, MessageFileEvent.class, ChatStreamCallback::onMessageFile));
        chatEventHandlers.put(StreamEventType.TTS_MESSAGE, (callback, data) -> handleEvent(callback, data, TtsMessageEvent.class, ChatStreamCallback::onTTSMessage));
        chatEventHandlers.put(StreamEventType.TTS_MESSAGE_END, (callback, data) -> handleEvent(callback, data, TtsMessageEndEvent.class, ChatStreamCallback::onTTSMessageEnd));
        chatEventHandlers.put(StreamEventType.MESSAGE_REPLACE, (callback, data) -> handleEvent(callback, data, MessageReplaceEvent.class, ChatStreamCallback::onMessageReplace));
        chatEventHandlers.put(StreamEventType.AGENT_MESSAGE, (callback, data) -> handleEvent(callback, data, AgentMessageEvent.class, ChatStreamCallback::onAgentMessage));
        chatEventHandlers.put(StreamEventType.AGENT_THOUGHT, (callback, data) -> handleEvent(callback, data, AgentThoughtEvent.class, ChatStreamCallback::onAgentThought));
        chatEventHandlers.put(StreamEventType.ERROR, (callback, data) -> handleEvent(callback, data, ErrorEvent.class, ChatStreamCallback::onError));
        chatEventHandlers.put(StreamEventType.PING, (callback, data) -> handleEvent(callback, data, PingEvent.class, ChatStreamCallback::onPing));

        // Initialize completion event handlers
        completionEventHandlers.put(StreamEventType.MESSAGE, (callback, data) -> handleEvent(callback, data, MessageEvent.class, CompletionStreamCallback::onMessage));
        completionEventHandlers.put(StreamEventType.MESSAGE_END, (callback, data) -> handleEvent(callback, data, MessageEndEvent.class, CompletionStreamCallback::onMessageEnd));
        completionEventHandlers.put(StreamEventType.TTS_MESSAGE, (callback, data) -> handleEvent(callback, data, TtsMessageEvent.class, CompletionStreamCallback::onTtsMessage));
        completionEventHandlers.put(StreamEventType.TTS_MESSAGE_END, (callback, data) -> handleEvent(callback, data, TtsMessageEndEvent.class, CompletionStreamCallback::onTtsMessageEnd));
        completionEventHandlers.put(StreamEventType.MESSAGE_REPLACE, (callback, data) -> handleEvent(callback, data, MessageReplaceEvent.class, CompletionStreamCallback::onMessageReplace));
        completionEventHandlers.put(StreamEventType.ERROR, (callback, data) -> handleEvent(callback, data, ErrorEvent.class, CompletionStreamCallback::onError));
        completionEventHandlers.put(StreamEventType.PING, (callback, data) -> handleEvent(callback, data, PingEvent.class, CompletionStreamCallback::onPing));

        // Initialize workflow event handlers
        workflowEventHandlers.put(StreamEventType.WORKFLOW_STARTED, (callback, data) -> handleEvent(callback, data, WorkflowStartedEvent.class, WorkflowStreamCallback::onWorkflowStarted));
        workflowEventHandlers.put(StreamEventType.NODE_STARTED, (callback, data) -> handleEvent(callback, data, NodeStartedEvent.class, WorkflowStreamCallback::onNodeStarted));
        workflowEventHandlers.put(StreamEventType.NODE_FINISHED, (callback, data) -> handleEvent(callback, data, NodeFinishedEvent.class, WorkflowStreamCallback::onNodeFinished));
        workflowEventHandlers.put(StreamEventType.WORKFLOW_FINISHED, (callback, data) -> handleEvent(callback, data, WorkflowFinishedEvent.class, WorkflowStreamCallback::onWorkflowFinished));
        workflowEventHandlers.put(StreamEventType.WORKFLOW_TEXT_CHUNK, (callback, data) -> handleEvent(callback, data, WorkflowTextChunkEvent.class, WorkflowStreamCallback::onWorkflowTextChunk));
        workflowEventHandlers.put(StreamEventType.TTS_MESSAGE, (callback, data) -> handleEvent(callback, data, TtsMessageEvent.class, WorkflowStreamCallback::onTtsMessage));
        workflowEventHandlers.put(StreamEventType.TTS_MESSAGE_END, (callback, data) -> handleEvent(callback, data, TtsMessageEndEvent.class, WorkflowStreamCallback::onTtsMessageEnd));
        workflowEventHandlers.put(StreamEventType.PING, (callback, data) -> handleEvent(callback, data, PingEvent.class, WorkflowStreamCallback::onPing));
        workflowEventHandlers.put(StreamEventType.ERROR, (callback, data) -> handleEvent(callback, data, ErrorEvent.class, WorkflowStreamCallback::onError));
    }

    public static void dispatchChatFlowEvent(ChatflowStreamCallback callback, String data, String StreamEventType) {
        dispatchEvent(callback, data, StreamEventType, chatFlowEventHandlers);
    }

    public static void dispatchChatEvent(ChatStreamCallback callback, String data, String StreamEventType) {
        dispatchEvent(callback, data, StreamEventType, chatEventHandlers);
    }

    public static void dispatchCompletionEvent(CompletionStreamCallback callback, String data) {
        BaseEvent baseEvent = JSONUtils.fromJson(data, BaseEvent.class);
        if (baseEvent == null) {
            log.warn("process event data null: {}", data);
            return;
        }
        String StreamEventTypeStr = baseEvent.getEvent();
        dispatchEvent(callback, data, StreamEventTypeStr, completionEventHandlers);
    }

    public static void dispatchWorkflowEvent(WorkflowStreamCallback callback, String data) {
        BaseEvent baseEvent = JSONUtils.fromJson(data, BaseEvent.class);
        if (baseEvent == null) {
            log.warn("process event data null: {}", data);
            return;
        }
        String StreamEventTypeStr = baseEvent.getEvent();
        dispatchEvent(callback, data, StreamEventTypeStr, workflowEventHandlers);
    }

    private static <T, C> void dispatchEvent(C callback, String data, String StreamEventTypeStr, Map<StreamEventType, BiConsumer<C, String>> handlers) {
        try {
            StreamEventType type = StreamEventType.fromValue(StreamEventTypeStr);
            if (type == null) {
                log.warn("unknow event type: {}", StreamEventTypeStr);
                return;
            }

            BiConsumer<C, String> handler = handlers.get(type);
            if (handler != null) {
                handler.accept(callback, data);
            } else {
                log.warn("not process event type: {}", StreamEventTypeStr);
            }
        } catch (Exception e) {
            log.error("callback exception: {}", e.getMessage(), e);
            try {
                if (callback instanceof ChatflowStreamCallback) {
                    ((ChatflowStreamCallback) callback).onException(e);
                } else if (callback instanceof ChatStreamCallback) {
                    ((ChatStreamCallback) callback).onException(e);
                } else if (callback instanceof CompletionStreamCallback) {
                    ((CompletionStreamCallback) callback).onException(e);
                } else if (callback instanceof WorkflowStreamCallback) {
                    ((WorkflowStreamCallback) callback).onException(e);
                }
            } catch (Exception ex) {
                log.error("callback exception", ex);
            }
        }
    }

    private static <T, C> void handleEvent(C callback, String data, Class<T> clazz, BiConsumer<C, T> consumer) {
        try {
            T event = JSONUtils.fromJson(data, clazz);
            consumer.accept(callback, event);
        } catch (Exception e) {
            log.error("Exception occurred while parsing event data: {}", e.getMessage(), e);
            throw e;
        }
    }
}
