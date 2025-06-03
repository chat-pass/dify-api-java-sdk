package io.github.chatpass.dify.data.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * agent模式下返回的文本内容
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AgentMessageEvent extends BaseMessageEvent {

    /**
     * LLM 返回文本块内容
     */
    private String answer;
}
