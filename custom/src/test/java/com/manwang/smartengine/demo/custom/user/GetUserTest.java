package com.manwang.smartengine.demo.custom.user;

import com.alibaba.smart.framework.engine.SmartEngine;
import com.alibaba.smart.framework.engine.model.instance.InstanceStatus;
import com.alibaba.smart.framework.engine.model.instance.ProcessInstance;
import com.alibaba.smart.framework.engine.persister.custom.session.PersisterSession;
import com.alibaba.smart.framework.engine.service.command.ProcessCommandService;
import com.manwang.smartengine.demo.custom.user.constants.GetUserParamConstants;
import com.manwang.smartengine.demo.custom.user.facade.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@Slf4j
public class GetUserTest {

    @Autowired
    private SmartEngine smartEngine;

    @Test
    public void testGetUser() {
        try {
            PersisterSession.create();
            ProcessCommandService processCommandService = smartEngine.getProcessCommandService();
            Map<String, Object> request = new HashMap<>();
            Map<String, Object> response = new HashMap<>();
            request.put(GetUserParamConstants.USER_ID_PARAM, UUID.randomUUID().toString());
            ProcessInstance processInstance = processCommandService.start("get_user", "1.0.0", request, response);
            if (!processInstance.getStatus().equals(InstanceStatus.completed)) {
                throw new RuntimeException("运行异常");
            }
            UserResponse userResponse = (UserResponse) response.get(GetUserParamConstants.USER_RESULT_PARAM);
            if (userResponse == null) {
                log.warn("未获取到用户信息");
            } else {
                log.info("{}", userResponse);
            }
        } finally {
            PersisterSession.destroySession();
        }
    }
}
