package com.manwang.smartengine.demo.custom.order.dao;

import com.manwang.smartengine.demo.custom.order.po.OrderPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {

    int insert(OrderPO orderProcess);

    OrderPO selectOrderById(Long id);

    int updateOrderProcess(OrderPO orderPO);
}
