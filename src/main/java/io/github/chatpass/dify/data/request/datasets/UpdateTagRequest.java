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
public class UpdateTagRequest {

    /**
     * 修改后的标签名称（必填，最大长度为50）
     */
    private String name;

    /**
     * 标签ID（必填）
     */
    @JsonProperty("tag_id")
    private String tagId;
} 