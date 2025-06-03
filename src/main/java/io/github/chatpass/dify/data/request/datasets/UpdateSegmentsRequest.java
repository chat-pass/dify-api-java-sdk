package io.github.chatpass.dify.data.request.datasets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UpdateSegmentsRequest {

    /**
     * 分段信息
     */
    private Segment segment;

    /**
     * 分段更新信息
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

        /**
         * 是否启用（非必填）
         */
        private Boolean enabled;

        /**
         * 是否重新生成子分段（非必填）
         */
        @JsonProperty("regenerate_child_chunks")
        private Boolean regenerateChildChunks;
    }
} 