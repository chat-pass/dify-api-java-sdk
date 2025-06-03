package io.github.chatpass.dify.data.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 思考步骤事件
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AgentThoughtEvent extends BaseMessageEvent {

    /**
     * agent_thought ID，每一轮Agent迭代都会有一个唯一的id
     */
    private String id;

    /**
     * agent_thought在消息中的位置，如第一轮迭代position为1
     */
    private Integer position;

    /**
     * agent的思考内容
     */
    private String thought;

    /**
     * 工具调用的返回结果
     */
    private String observation;

    /**
     * 使用的工具列表，以 ; 分割多个工具
     */
    private String tool;

    /**
     * 工具的输入，JSON格式的字符串(object)
     */
    private String toolInput;

    /**
     * 当前 agent_thought 关联的文件ID
     */
    private List<String> messageFiles;

    /**
     * 文件ID
     */
    private String fileId;
}
