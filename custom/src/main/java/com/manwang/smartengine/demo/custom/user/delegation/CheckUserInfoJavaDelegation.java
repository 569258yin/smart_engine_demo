package com.manwang.smartengine.demo.custom.user.delegation;

import com.alibaba.smart.framework.engine.context.ExecutionContext;
import com.alibaba.smart.framework.engine.delegation.JavaDelegation;
import com.manwang.smartengine.demo.custom.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CheckUserInfoJavaDelegation implements JavaDelegation {

    @Override
    public void execute(ExecutionContext executionContext) {
        log.info("Task1 判断用户信息 request={},response={}", executionContext.getRequest(), executionContext.getResponse());
        User user = (User) executionContext.getResponse().get("user");
        if (user == null || user.getAge() < 20) {
            executionContext.getRequest().put("checkSuccess", 0);
        } else {
            executionContext.getRequest().put("checkSuccess", 1);
        }
    }


}
