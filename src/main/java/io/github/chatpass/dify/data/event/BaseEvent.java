package io.github.chatpass.dify.data.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.chatpass.dify.data.enums.StreamEventType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseEvent {

    /**
     * 事件类型
     */
    private String event;

    /**
     * 创建时间戳
     */
    private Long createdAt;

    /**
     * 任务ID，用于请求跟踪和停止响应
     */
    private String taskId;

    /**
     * 获取事件类型
     * @return 事件类型枚举
     */
    public StreamEventType getEventType() {
        return StreamEventType.fromValue(event);
    }
}
