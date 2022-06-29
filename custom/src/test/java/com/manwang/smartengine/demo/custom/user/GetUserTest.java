package com.manwang.smartengine.demo.custom.user;

import com.alibaba.smart.framework.engine.SmartEngine;
import com.alibaba.smart.framework.engine.model.instance.ProcessInstance;
import com.alibaba.smart.framework.engine.persister.custom.session.PersisterSession;
import com.alibaba.smart.framework.engine.service.command.ProcessCommandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
public class GetUserTest {

    @Autowired
    private SmartEngine smartEngine;

    @Test
    public void testGetUser() {
        try {
            PersisterSession.create();
            ProcessCommandService processCommandService = smartEngine.getProcessCommandService();
            Map<String, Object> request = new HashMap<>();
            request.put("userId", UUID.randomUUID().toString());
            ProcessInstance processInstance = processCommandService.start("get_user", "1.0.0", request);
        } finally {
            PersisterSession.destroySession();
        }
    }
}
