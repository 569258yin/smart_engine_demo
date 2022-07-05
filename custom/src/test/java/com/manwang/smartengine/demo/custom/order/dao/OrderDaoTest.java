package com.manwang.smartengine.demo.custom.order.dao;

import com.manwang.smartengine.demo.custom.order.po.OrderPO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OrderDaoTest {
    @Autowired
    private OrderDao orderDao;

    private OrderPO orderProcess;

    @BeforeEach
    void setUp() {
        orderProcess = new OrderPO();
        orderProcess.setProcessInstance("test");
        orderProcess.setProcessInstanceId(123L);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @Transactional
    @Rollback
    void insert() {
        int i = orderDao.insert(orderProcess);
        Assertions.assertEquals(i, 1);
        OrderPO db = orderDao.selectOrderById(orderProcess.getId());
        Assertions.assertNotNull(db);
        Assertions.assertEquals(db.getProcessInstanceId(), orderProcess.getProcessInstanceId());
        db.setProcessInstance("update");
        orderDao.updateOrderProcess(db);
        db = orderDao.selectOrderById(orderProcess.getId());
        Assertions.assertEquals("update", db.getProcessInstance());
    }

}