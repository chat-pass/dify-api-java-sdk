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
public class DatasetListResponse {
    /**
     * 数据集列表
     */
    private List<DatasetResponse> data;

    /**
     * 是否有更多数据
     */
    private Boolean hasMore;

    /**
     * 每页限制
     */
    private Integer limit;

    /**
     * 总数
     */
    private Integer total;

    /**
     * 当前页码
     */
    private Integer page;
} 