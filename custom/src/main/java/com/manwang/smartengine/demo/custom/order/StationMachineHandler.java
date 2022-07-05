package com.manwang.smartengine.demo.custom.order;

import com.alibaba.smart.framework.engine.SmartEngine;
import com.alibaba.smart.framework.engine.model.instance.ExecutionInstance;
import com.alibaba.smart.framework.engine.model.instance.ProcessInstance;
import com.alibaba.smart.framework.engine.persister.custom.session.PersisterSession;
import com.alibaba.smart.framework.engine.persister.util.InstanceSerializerFacade;
import com.alibaba.smart.framework.engine.service.command.ExecutionCommandService;
import com.alibaba.smart.framework.engine.service.command.ProcessCommandService;
import com.alibaba.smart.framework.engine.service.query.ExecutionQueryService;
import com.google.common.collect.Maps;
import com.manwang.smartengine.demo.custom.order.dao.OrderDao;
import com.manwang.smartengine.demo.custom.order.exception.OrderStatePlatformException;
import com.manwang.smartengine.demo.custom.order.po.OrderPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class StationMachineHandler {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SmartEngine smartEngine;

    public static final String PROCESS_ID = "order_test";
    public static final String PROCESS_VERSION = "1.0.0";

    /**
     * 启动状态机
     */
    public String startStationMachine(Map<String, Object> request) {
        log.info("startStationMachine enter");
        try {
            ProcessCommandService processCommandService = smartEngine.getProcessCommandService();
            PersisterSession.create();
            ProcessInstance processInstance = processCommandService.start(PROCESS_ID, PROCESS_VERSION, request);
            String serializedProcessInstance = InstanceSerializerFacade.serialize(processInstance);
            log.info("startStationMachine success,serializedProcessInstance:{}", serializedProcessInstance);
            return serializedProcessInstance;
        } catch (Exception e) {
            log.error("创建流程启动状态机异常:{}", e.getMessage(), e);
            throw e;
        } finally {
            PersisterSession.destroySession();
        }
    }

    /**
     * 驱动状态机
     */
    public void signalStationMachine(Long orderId, String activityId, Map<String, Object> request) {
        log.info("signalStationMachine process enter,orderId:{},activityId:{}", orderId, activityId);
        try {
            PersisterSession.create();
            ExecutionQueryService executionQueryService = smartEngine.getExecutionQueryService();
            ExecutionCommandService executionCommandService = smartEngine.getExecutionCommandService();
            //获取持久化的状态机数据
            OrderPO order = orderDao.selectOrderById(orderId);

            log.info("orderId:{},activityId:{},processInstance is:{}", orderId, activityId, order.getProcessInstance());
            //恢复状态机实例
            ProcessInstance processInstance = InstanceSerializerFacade.deserializeAll(order.getProcessInstance());
            PersisterSession.currentSession().putProcessInstance(processInstance);

            //启动状态机流转
            List<ExecutionInstance> executionInstanceList = executionQueryService.findActiveExecutionList(processInstance.getInstanceId());
            if (CollectionUtils.isEmpty(executionInstanceList)) {
                log.error("orderId:{},activityId:{},executionInstanceList is null for OrderId ", orderId, activityId);
                throw new OrderStatePlatformException("未找到对应的流程实例");
            }
            boolean found = false;
            Map<String, Object> response = Maps.newHashMap();
            for (ExecutionInstance executionInstance : executionInstanceList) {
                if (executionInstance.getProcessDefinitionActivityId().equals(activityId)) {
                    found = true;
                    processInstance = executionCommandService.signal(executionInstance.getInstanceId(), request, response);
                    //再次持久化状态机数据
                    String serializedProcessInstance = InstanceSerializerFacade.serialize(processInstance);
                    log.info("orderId:{},processInstance is ,{}", orderId, processInstance);
                    saveStationMachineProcessInstance(orderId, serializedProcessInstance);
                }
            }
            if (!found) {
                log.error("orderId:{},activityId:{},No active executionInstance found for afterSaleOrderId ", orderId, activityId);
                throw new OrderStatePlatformException("未找到可以执行的流程实例");
            }
            log.info("signalStationMachine process success,orderId:{},activityId:{}", orderId, activityId);
        } catch (Throwable e) {
            log.error("orderId:{},驱动状态机异常:{}", orderId, e.getMessage(), e);
            throw e;
        } finally {
            PersisterSession.destroySession();
        }
    }

    /**
     * 保存状态机数据
     */
    public void saveStationMachineProcessInstance(Long orderId, String serializedProcessInstance) {
        OrderPO order = new OrderPO();
        order.setId(orderId);
        order.setProcessInstance(serializedProcessInstance);

        try {
            int result = orderDao.updateOrderProcess(order);
            if (result <= 0) {
                log.error("orderId:{},保存状态机数据更新单据异常", orderId);
                throw new RuntimeException("orderSaleSelective result failed!");
            }
            log.info("orderId:{},保存状态机数据成功,serializedProcessInstance:{},", orderId, serializedProcessInstance);
        } catch (Exception e) {
            log.error("orderId:{},保存状态机实例异常:{}", orderId, e.getMessage(), e);
            throw new RuntimeException("saveStationMachineProcessInstance failed!");
        }
    }
}
