package io.github.chatpass.dify.data.request;

import io.github.chatpass.dify.data.enums.FileTransferMethod;
import io.github.chatpass.dify.data.enums.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    /**
     * 文件类型
     */
    private FileType type;

    /**
     * 传输方式
     */
    private FileTransferMethod transferMethod;

    /**
     * 文件 URL（当传输方式为 REMOTE_URL 时使用）
     */
    private String url;

    /**
     * 上传文件 ID（当传输方式为 LOCAL_FILE 时使用）
     */
    private String uploadFileId;
}
