package io.github.chatpass.dify.data.response;

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
public class SuggestedQuestionsResponse {
    /**
     * 结果
     */
    private String result;

    /**
     * 建议问题列表
     */
    private List<String> data;
}
