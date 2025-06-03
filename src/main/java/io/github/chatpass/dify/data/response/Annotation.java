package io.github.chatpass.dify.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Annotation {

    /**
     * 标注 ID
     */
    private String id;

    /**
     * 问题
     */
    private String question;

    /**
     * 答案内容
     */
    private String answer;

    /**
     * 命中次数
     */
    private Integer hitCount;

    /**
     * 创建时间
     */
    private Long createdAt;
}
