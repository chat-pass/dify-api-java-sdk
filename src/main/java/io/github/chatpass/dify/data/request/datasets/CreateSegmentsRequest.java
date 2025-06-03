package io.github.chatpass.dify.data.request.datasets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateSegmentsRequest {

    /**
     * 分段列表
     */
    private List<Segment> segments;

    /**
     * 分段信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Segment {

        /**
         * 文本内容/问题内容（必填）
         */
        private String content;

        /**
         * 答案内容（非必填，如果知识库的模式为 Q&A 模式则传值）
         */
        private String answer;

        /**
         * 关键字（非必填）
         */
        private List<String> keywords;
    }
} 