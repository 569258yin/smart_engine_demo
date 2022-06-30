package com.manwang.smartengine.demo.custom.user.delegation;

import com.alibaba.smart.framework.engine.context.ExecutionContext;
import com.alibaba.smart.framework.engine.delegation.JavaDelegation;
import com.manwang.smartengine.demo.custom.user.constants.GetUserParamConstants;
import com.manwang.smartengine.demo.custom.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class GetUserInfoJavaDelegation implements JavaDelegation {

    @Override
    public void execute(ExecutionContext executionContext) {
        String userId = (String) executionContext.getRequest().get("userId");
        log.info("Task0 获取用户基本信息,userId={}", userId);
        executionContext.getResponse().put(GetUserParamConstants.USER_PARAM, buildUser());
    }


    private User buildUser() {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName("小明");
        user.setAge(RandomUtils.nextInt(10, 50));
        return user;
    }
}
