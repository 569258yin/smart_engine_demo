package com.manwang.smartengine.demo.custom.order.service;

import com.manwang.smartengine.demo.custom.order.po.OrderPO;

public interface OrderService {
    void createOrder(OrderPO orderPO);

    void startAudit(Long id);

    void audit(Long id, String agree);
}
