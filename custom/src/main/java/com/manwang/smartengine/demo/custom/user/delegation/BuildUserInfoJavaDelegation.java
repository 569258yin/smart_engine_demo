package com.manwang.smartengine.demo.custom.user.delegation;

import com.alibaba.smart.framework.engine.context.ExecutionContext;
import com.alibaba.smart.framework.engine.delegation.JavaDelegation;
import com.manwang.smartengine.demo.custom.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class BuildUserInfoJavaDelegation implements JavaDelegation {

    @Override
    public void execute(ExecutionContext executionContext) {
        log.info("组装用户信息 request={},response={}", executionContext.getRequest(), executionContext.getResponse());
    }


    private User buildUser() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(RandomStringUtils.random(10));
        user.setDelete(RandomUtils.nextInt(0, 1));
        return user;
    }
}
