package com.manwang.smartengine.demo.custom.user.delegation;

import com.alibaba.smart.framework.engine.context.ExecutionContext;
import com.alibaba.smart.framework.engine.delegation.JavaDelegation;
import com.manwang.smartengine.demo.custom.user.constants.GetUserParamConstants;
import com.manwang.smartengine.demo.custom.user.model.UserCar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;

@Component
@Slf4j
public class GetUserCarJavaDelegation implements JavaDelegation {

    @Override
    public void execute(ExecutionContext executionContext) {
        log.info("Task4 获取用户车型信息 request={},response={}", executionContext.getRequest(), executionContext.getResponse());
        String userId = GetUserParamConstants.getUserId(executionContext.getRequest());
        if (userId == null) {
            throw new InvalidParameterException("userId错误");
        }
        executionContext.getResponse().put(GetUserParamConstants.USER_CAR_PARAM, getUserCarById(userId));
    }


    private UserCar getUserCarById(String userId) {
        UserCar userCar = new UserCar();
        userCar.setUserId(userId);
        userCar.setBrand("奥迪");
        userCar.setModel("A6L");
        userCar.setDisplacement("2.0T");
        userCar.setVehicle("2022 两驱尊享款");
        return userCar;
    }

}
