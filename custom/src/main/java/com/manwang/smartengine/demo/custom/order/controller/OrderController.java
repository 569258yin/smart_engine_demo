package com.manwang.smartengine.demo.custom.order.controller;

import com.manwang.smartengine.demo.custom.order.po.OrderPO;
import com.manwang.smartengine.demo.custom.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("createOrder")
    public Long createOrder(@RequestParam("orderNo") String orderNo) {
        OrderPO orderPO = new OrderPO();
        orderPO.setOrderNo(orderNo);
        orderService.createOrder(orderPO);
        return orderPO.getId();
    }

    @PostMapping("startAudit")
    public String startAudit(@RequestParam("id") Long id) {
        orderService.startAudit(id);
        return "SUCCESS";
    }

    @PostMapping("audit")
    public String audit(@RequestParam("id") Long id, @RequestParam("agree") String agree) {
        orderService.audit(id, agree);
        return "SUCCESS";
    }
}
