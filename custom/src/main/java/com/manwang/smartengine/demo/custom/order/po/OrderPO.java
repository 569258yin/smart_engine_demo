package com.manwang.smartengine.demo.custom.order.po;

import lombok.Data;

@Data
public class OrderPO {

    private Long id;
    private String orderNo;
    private Long processInstanceId;
    public String processInstance;


}
