package com.manwang.smartengine.demo.database.test;

import com.alibaba.smart.framework.engine.SmartEngine;
import com.alibaba.smart.framework.engine.model.assembly.ProcessDefinition;
import com.alibaba.smart.framework.engine.model.assembly.ProcessDefinitionSource;
import com.alibaba.smart.framework.engine.model.instance.DeploymentInstance;
import com.alibaba.smart.framework.engine.model.instance.ExecutionInstance;
import com.alibaba.smart.framework.engine.model.instance.InstanceStatus;
import com.alibaba.smart.framework.engine.model.instance.ProcessInstance;
import com.alibaba.smart.framework.engine.service.command.DeploymentCommandService;
import com.alibaba.smart.framework.engine.service.command.ExecutionCommandService;
import com.alibaba.smart.framework.engine.service.command.ProcessCommandService;
import com.alibaba.smart.framework.engine.service.command.RepositoryCommandService;
import com.alibaba.smart.framework.engine.service.param.command.CreateDeploymentCommand;
import com.alibaba.smart.framework.engine.service.query.DeploymentQueryService;
import com.alibaba.smart.framework.engine.service.query.ExecutionQueryService;
import com.alibaba.smart.framework.engine.service.query.ProcessQueryService;
import com.alibaba.smart.framework.engine.service.query.RepositoryQueryService;
import com.alibaba.smart.framework.engine.util.IOUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class SmartTest {

    @Autowired
    private SmartEngine smartEngine;


    @Test
    @DisplayName("读取配置文件实例化流程模板")
    public void testRepositoryCommandService() {
        RepositoryCommandService repositoryCommandService = smartEngine.getRepositoryCommandService();
        ProcessDefinitionSource definitionSource = repositoryCommandService.deploy("smart-engine/order.bpmn20.xml");
        Assertions.assertEquals(1, definitionSource.getProcessDefinitionList().size());
        Assertions.assertEquals(14, definitionSource.getProcessDefinitionList().get(0).getBaseElementList().size());
    }

    @Test
    @DisplayName("查询内存中的流程模板实例")
    public void testRepositoryQueryService() {
        RepositoryCommandService repositoryCommandService = smartEngine.getRepositoryCommandService();
        ProcessDefinitionSource definitionSource = repositoryCommandService.deploy("smart-engine/order.xml.old");

        RepositoryQueryService repositoryQueryService = smartEngine.getRepositoryQueryService();
        ProcessDefinition processDefinition = repositoryQueryService.getCachedProcessDefinition("order", "1.0.0");
        Assertions.assertEquals(definitionSource.getProcessDefinitionList().get(0).getId(), processDefinition.getId());
    }

    @Test
    @DisplayName("发布流程定义")
    public void testDeploymentCommandService() {
        DeploymentCommandService deploymentCommandService = smartEngine.getDeploymentCommandService();
        String content = IOUtil.readResourceFileAsUTF8String("smart-engine/order.bpmn20.xml");
        CreateDeploymentCommand createDeploymentCommand = new CreateDeploymentCommand();
        createDeploymentCommand.setProcessDefinitionCode("order");
        createDeploymentCommand.setProcessDefinitionName("order");
        createDeploymentCommand.setDeploymentUserId("manwang");
        createDeploymentCommand.setProcessDefinitionContent(content);
        createDeploymentCommand.setProcessDefinitionDesc("test order");
        createDeploymentCommand.setProcessDefinitionType("test");
        createDeploymentCommand.setDeploymentStatus("new");
        DeploymentInstance instance = deploymentCommandService.createDeployment(createDeploymentCommand);
        instance.getDeploymentStatus();
    }

    @Test
    @DisplayName("查询流程定义内容")
    public void testDeploymentQueryService() {
        DeploymentQueryService deploymentQueryService = smartEngine.getDeploymentQueryService();
        DeploymentInstance deploymentInstance = deploymentQueryService.findById("test");
    }


    @Test
    @DisplayName("流程实例管理")
    public void testProcessCommandService() {
        Map<String, Object> request = new HashMap<>();
        ProcessCommandService processCommandService = smartEngine.getProcessCommandService();
        ProcessInstance processInstance = processCommandService.start("order", "1.0.0", request);
    }

    @Test
    @DisplayName("流程实例查询")
    public void testProcessQueryService() {
        ProcessQueryService processQueryService = smartEngine.getProcessQueryService();
        ProcessInstance processInstance = processQueryService.findById("297078328307744768");
        Assertions.assertEquals(InstanceStatus.running, processInstance.getStatus());
    }

    @Test
    @DisplayName("唤起人工审核节点")
    public void testExecutionCommandService() {
        // 针对userTask进行唤醒
        ExecutionCommandService executionCommandService = smartEngine.getExecutionCommandService();
        // 流程查询器
        ProcessQueryService processQueryService = smartEngine.getProcessQueryService();
        // 执行记录查询器
        ExecutionQueryService executionQueryService = smartEngine.getExecutionQueryService();
        ProcessInstance processInstance = processQueryService.findById("297181418952327168");
        // 获取当前激活的节点
        List<ExecutionInstance> activeExecutionInstances = executionQueryService.findActiveExecutionList(processInstance.getInstanceId());
        Map<String, Object> request = new HashMap<>();
        request.put("approve", "agree");
        for (ExecutionInstance activeExecutionInstance : activeExecutionInstances) {
            if (activeExecutionInstance.getProcessDefinitionActivityId().equals("Activity_0sovwe9")) {
                executionCommandService.signal(activeExecutionInstance.getInstanceId(), request);
            }
        }
    }
}
