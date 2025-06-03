package io.github.chatpass.dify.data.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TtsMessageEvent extends BaseMessageEvent {
    /**
     * 语音合成之后的音频块使用 Base64 编码之后的文本内容
     */
    private String audio;
}
