package com.manwang.smartengine.demo.custom.order.exception;

import com.manwang.smartengine.demo.custom.order.context.OrderStateContext;

public class OrderStatePlatformException extends RuntimeException {
    private OrderStateContext orderStateContext;
    private Long processInstanceId;


    public OrderStatePlatformException(OrderStateContext orderStateContext, Long processInstanceId) {
        this.orderStateContext = orderStateContext;
        this.processInstanceId = processInstanceId;
    }

    public OrderStatePlatformException(String message, OrderStateContext orderStateContext, Long processInstanceId) {
        super(message);
        this.orderStateContext = orderStateContext;
        this.processInstanceId = processInstanceId;
    }

    public OrderStatePlatformException(String message) {
        super(message);
    }
}
