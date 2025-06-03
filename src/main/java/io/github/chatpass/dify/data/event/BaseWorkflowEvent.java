package io.github.chatpass.dify.data.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BaseWorkflowEvent extends BaseEvent{
    /**
     * workflow 执行 ID
     */
    private String workflowRunId;
}
