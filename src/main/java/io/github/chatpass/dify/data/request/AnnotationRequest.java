package io.github.chatpass.dify.data.request;

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
public class AnnotationRequest {

    /**
     * 问题
     */
    private String question;

    /**
     * 答案内容
     */
    private String answer;
    
}
