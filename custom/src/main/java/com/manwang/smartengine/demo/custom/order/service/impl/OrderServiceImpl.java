package com.manwang.smartengine.demo.custom.order.service.impl;

import com.manwang.smartengine.demo.custom.order.StationMachineHandler;
import com.manwang.smartengine.demo.custom.order.dao.OrderDao;
import com.manwang.smartengine.demo.custom.order.po.OrderPO;
import com.manwang.smartengine.demo.custom.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private StationMachineHandler stationMachineHandler;
    @Autowired
    private OrderDao orderDao;

    @Override
    public void createOrder(OrderPO orderPO) {
        Map<String, Object> request = new HashMap<>();
        request.put("orderNo", orderPO.getOrderNo());
        String processInstance = stationMachineHandler.startStationMachine(request);
        orderPO.setProcessInstance(processInstance);
        orderDao.insert(orderPO);
    }

    @Override
    public void startAudit(Long id) {
        OrderPO order = orderDao.selectOrderById(id);
        Assert.notNull(order, "订单不存在");
        Map<String, Object> request = new HashMap<>();
        stationMachineHandler.signalStationMachine(id, "Activity_Start_Audit", request);
    }

    @Override
    public void audit(Long id, String agree) {
        OrderPO order = orderDao.selectOrderById(id);
        Assert.notNull(order, "订单不存在");
        Map<String, Object> request = new HashMap<>();
        request.put("agree", agree);
        stationMachineHandler.signalStationMachine(id, "Activity_Waiting_Result", request);
    }


}
