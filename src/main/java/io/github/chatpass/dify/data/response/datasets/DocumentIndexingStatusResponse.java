package io.github.chatpass.dify.data.response.datasets;

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
public class DocumentIndexingStatusResponse {

    /**
     * 文档索引状态列表
     */
    private List<DocumentIndexingInfo> data;

    /**
     * 文档索引状态信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DocumentIndexingInfo {

        /**
         * 文档ID
         */
        private String id;

        /**
         * 索引状态
         */
        @JsonProperty("indexing_status")
        private String indexingStatus;

        /**
         * 处理开始时间
         */
        @JsonProperty("processing_started_at")
        private Double processingStartedAt;

        /**
         * 解析完成时间
         */
        @JsonProperty("parsing_completed_at")
        private Double parsingCompletedAt;

        /**
         * 清洗完成时间
         */
        @JsonProperty("cleaning_completed_at")
        private Double cleaningCompletedAt;

        /**
         * 分割完成时间
         */
        @JsonProperty("splitting_completed_at")
        private Double splittingCompletedAt;

        /**
         * 完成时间
         */
        @JsonProperty("completed_at")
        private Double completedAt;

        /**
         * 暂停时间
         */
        @JsonProperty("paused_at")
        private Double pausedAt;

        /**
         * 错误信息
         */
        private String error;

        /**
         * 停止时间
         */
        @JsonProperty("stopped_at")
        private Double stoppedAt;

        /**
         * 已完成分段数
         */
        @JsonProperty("completed_segments")
        private Integer completedSegments;

        /**
         * 总分段数
         */
        @JsonProperty("total_segments")
        private Integer totalSegments;
    }
} 