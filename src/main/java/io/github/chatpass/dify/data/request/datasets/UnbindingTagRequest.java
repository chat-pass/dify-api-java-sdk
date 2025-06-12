package io.github.chatpass.dify.data.request.datasets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UnbindingTagRequest {

    /**
     * 标签ID（必填）
     */
    @JsonProperty("tag_id")
    private String tagId;

    /**
     * 知识库ID（必填）
     */
    @JsonProperty("target_id")
    private String targetId;
} 