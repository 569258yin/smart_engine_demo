package com.manwang.smartengine.demo.database.config;

import com.alibaba.smart.framework.engine.configuration.TaskAssigneeDispatcher;
import com.alibaba.smart.framework.engine.constant.AssigneeTypeConstant;
import com.alibaba.smart.framework.engine.model.assembly.Activity;
import com.alibaba.smart.framework.engine.model.instance.TaskAssigneeCandidateInstance;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class OrderTaskAssigneeDispatcher implements TaskAssigneeDispatcher {


    @Override
    public List<TaskAssigneeCandidateInstance> getTaskAssigneeCandidateInstance(Activity activity, Map<String, Object> request) {
        List<TaskAssigneeCandidateInstance> assigneeLists = getTaskAssignees(activity.getId());
        sendMessage(activity.getId(), request);
        return assigneeLists;
    }

    /**
     * 对外发送一个需要人工处理的消息
     *
     * @param id
     * @param request
     */
    private void sendMessage(String id, Map<String, Object> request) {
        log.info("{}任务等待人工处理", id);
    }

    /**
     * 根据活动返回对应的审核人
     *
     * @param id
     * @return
     */
    private List<TaskAssigneeCandidateInstance> getTaskAssignees(String id) {
        List<TaskAssigneeCandidateInstance> taskAssigneeCandidateInstanceList = new ArrayList<TaskAssigneeCandidateInstance>();

        TaskAssigneeCandidateInstance taskAssigneeCandidateInstance = new TaskAssigneeCandidateInstance();
        taskAssigneeCandidateInstance.setAssigneeId("1121");
        taskAssigneeCandidateInstance.setAssigneeType(AssigneeTypeConstant.USER);
        taskAssigneeCandidateInstanceList.add(taskAssigneeCandidateInstance);
        return taskAssigneeCandidateInstanceList;
    }

}
