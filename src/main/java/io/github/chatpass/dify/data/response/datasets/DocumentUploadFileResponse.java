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
public class DocumentUploadFileResponse {

    /**
     * 文件ID
     */
    private String id;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件扩展名
     */
    private String extension;

    /**
     * 预览URL
     */
    private String url;

    /**
     * 下载URL
     */
    @JsonProperty("download_url")
    private String downloadUrl;

    /**
     * MIME类型
     */
    @JsonProperty("mime_type")
    private String mimeType;

    /**
     * 创建者
     */
    @JsonProperty("created_by")
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonProperty("created_at")
    private Long createdAt;
} 