package io.github.chatpass.dify.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Workflow日志列表响应
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowLogListResponse {

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页条数
     */
    private Integer limit;

    /**
     * 总条数
     */
    private Integer total;

    /**
     * 是否还有更多数据
     */
    @JsonProperty("has_more")
    private Boolean hasMore;

    /**
     * 日志数据列表
     */
    private List<WorkflowLogData> data;

    /**
     * Workflow日志数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WorkflowLogData {

        /**
         * 日志ID
         */
        private String id;

        /**
         * Workflow执行日志
         */
        @JsonProperty("workflow_run")
        private WorkflowRunInfo workflowRun;

        /**
         * 来源
         */
        @JsonProperty("created_from")
        private String createdFrom;

        /**
         * 角色
         */
        @JsonProperty("created_by_role")
        private String createdByRole;

        /**
         * （可选）帐号
         */
        @JsonProperty("created_by_account")
        private String createdByAccount;

        /**
         * 用户
         */
        @JsonProperty("created_by_end_user")
        private EndUser createdByEndUser;

        /**
         * 创建时间
         */
        @JsonProperty("created_at")
        private Long createdAt;
    }

    /**
     * Workflow运行信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WorkflowRunInfo {

        /**
         * 标识
         */
        private String id;

        /**
         * 版本
         */
        private String version;

        /**
         * 执行状态：running, succeeded, failed, stopped
         */
        private String status;

        /**
         * 错误信息
         */
        private String error;

        /**
         * 耗时，单位秒
         */
        @JsonProperty("elapsed_time")
        private Long elapsedTime;

        /**
         * 消耗的token数量
         */
        @JsonProperty("total_tokens")
        private Long totalTokens;

        /**
         * 执行步骤长度
         */
        @JsonProperty("total_steps")
        private Integer totalSteps;

        /**
         * 开始时间
         */
        @JsonProperty("created_at")
        private Long createdAt;

        /**
         * 结束时间
         */
        @JsonProperty("finished_at")
        private Long finishedAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class EndUser {

        /**
         * 标识
         */
        private String id;

        /**
         * 类型
         */
        private String type;

        /**
         * 是否匿名
         */
        @JsonProperty("is_anonymous")
        private Boolean isAnonymous;

        /**
         * 会话标识
         */
        @JsonProperty("session_id")
        private String sessionId;
    }
} 