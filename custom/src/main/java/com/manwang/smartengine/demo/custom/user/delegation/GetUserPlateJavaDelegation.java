package com.manwang.smartengine.demo.custom.user.delegation;

import com.alibaba.smart.framework.engine.context.ExecutionContext;
import com.alibaba.smart.framework.engine.delegation.JavaDelegation;
import com.manwang.smartengine.demo.custom.user.constants.GetUserParamConstants;
import com.manwang.smartengine.demo.custom.user.model.UserPlate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;

@Component
@Slf4j
public class GetUserPlateJavaDelegation implements JavaDelegation {

    @Override
    public void execute(ExecutionContext executionContext) {
        log.info("Task2: 获取用户身份信息 request={},response={}", executionContext.getRequest(), executionContext.getResponse());
        String userId = GetUserParamConstants.getUserId(executionContext.getRequest());
        if (userId == null) {
            throw new InvalidParameterException("userId错误");
        }
        executionContext.getResponse().put(GetUserParamConstants.USER_PLATE_PARAM, getUserPlateCarById(userId));
    }

    private UserPlate getUserPlateCarById(String userId) {
        UserPlate userPlate = new UserPlate();
        userPlate.setUserId(userId);
        userPlate.setPlate("黄金会员");
        return userPlate;
    }

}
