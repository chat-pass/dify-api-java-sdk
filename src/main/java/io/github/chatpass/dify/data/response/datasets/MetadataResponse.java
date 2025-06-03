package io.github.chatpass.dify.data.response.datasets;

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
public class MetadataResponse {

    /**
     * 元数据ID
     */
    private String id;

    /**
     * 元数据类型
     */
    private String type;

    /**
     * 元数据名称
     */
    private String name;
} 