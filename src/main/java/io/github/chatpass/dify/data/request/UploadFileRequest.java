package io.github.chatpass.dify.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileRequest {
    /**
     * 用户标识
     */
    private String user;
}
