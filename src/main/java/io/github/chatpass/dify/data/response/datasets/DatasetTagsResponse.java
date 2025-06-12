package io.github.chatpass.dify.data.response.datasets;

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
public class DatasetTagsResponse {

    /**
     * 标签列表
     */
    private List<TagInfo> data;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 标签信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TagInfo {

        /**
         * 标签ID
         */
        private String id;

        /**
         * 标签名称
         */
        private String name;
    }
} 