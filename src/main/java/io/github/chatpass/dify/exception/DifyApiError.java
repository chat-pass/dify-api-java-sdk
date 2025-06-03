package io.github.chatpass.dify.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DifyApiError {
    private Integer status;
    private String code;
    private String message;

    public DifyApiError(Integer status,String message){
        this.status = status;
        this.message = message;
    }
}
