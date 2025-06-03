package io.github.chatpass.dify.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppParametersResponse {

    /**
     * 开场白
     */
    private String openingStatement;

    /**
     * 开场推荐问题列表
     */
    private List<String> suggestedQuestions;

    /**
     * 启用回答后给出推荐问题
     */
    private SuggestedQuestionsAfterAnswer suggestedQuestionsAfterAnswer;

    /**
     * 语音转文本
     */
    private SpeechToText speechToText;

    /**
     * 引用和归属
     */
    private RetrieverResource retrieverResource;

    /**
     * 标记回复
     */
    private AnnotationReply annotationReply;

    /**
     * 用户输入表单配置
     */
    private List<Map<String, Object>> userInputForm;

    /**
     * 文件上传配置
     */
    private FileUpload fileUpload;

    /**
     * 系统参数
     */
    private SystemParameters systemParameters;

    /**
     * 启用回答后给出推荐问题
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SuggestedQuestionsAfterAnswer {
        /**
         * 是否开启
         */
        private Boolean enabled;
    }

    /**
     * 语音转文本
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SpeechToText {
        /**
         * 是否开启
         */
        private Boolean enabled;
    }

    /**
     * 引用和归属
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RetrieverResource {
        /**
         * 是否开启
         */
        private Boolean enabled;
    }

    /**
     * 标记回复
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AnnotationReply {
        /**
         * 是否开启
         */
        private Boolean enabled;
    }

    /**
     * 文件上传配置
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FileUpload {
        /**
         * 图片设置
         */
        private Image image;

        /**
         * 图片设置
         */
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Image {
            /**
             * 是否开启
             */
            private Boolean enabled;

            /**
             * 图片数量限制
             */
            private Integer numberLimits;

            /**
             * 传递方式列表
             */
            private List<String> transferMethods;
        }
    }

    /**
     * 系统参数
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SystemParameters {
        /**
         * 文档上传大小限制 (MB)
         */
        private Integer fileSizeLimit;

        /**
         * 图片文件上传大小限制 (MB)
         */
        private Integer imageFileSizeLimit;

        /**
         * 音频文件上传大小限制 (MB)
         */
        private Integer audioFileSizeLimit;

        /**
         * 视频文件上传大小限制 (MB)
         */
        private Integer videoFileSizeLimit;
    }
}
