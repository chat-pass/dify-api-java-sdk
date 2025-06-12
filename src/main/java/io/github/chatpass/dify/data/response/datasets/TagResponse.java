package io.github.chatpass.dify.data.response.datasets;

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
public class TagResponse {

    /**
     * 标签ID
     */
    private String id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签类型
     */
    private String type;

    /**
     * 绑定数量
     */
    @JsonProperty("binding_count")
    private Integer bindingCount;
}