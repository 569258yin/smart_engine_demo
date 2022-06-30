package com.manwang.smartengine.demo.custom.user.delegation;

import com.alibaba.smart.framework.engine.context.ExecutionContext;
import com.alibaba.smart.framework.engine.delegation.JavaDelegation;
import com.manwang.smartengine.demo.custom.user.constants.GetUserParamConstants;
import com.manwang.smartengine.demo.custom.user.facade.UserResponse;
import com.manwang.smartengine.demo.custom.user.model.User;
import com.manwang.smartengine.demo.custom.user.model.UserAddress;
import com.manwang.smartengine.demo.custom.user.model.UserCar;
import com.manwang.smartengine.demo.custom.user.model.UserPlate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BuildUserInfoJavaDelegation implements JavaDelegation {

    @Override
    public void execute(ExecutionContext executionContext) {
        log.info("Task5 组装用户信息 request={},response={}", executionContext.getRequest(), executionContext.getResponse());
        User user = (User) executionContext.getResponse().get(GetUserParamConstants.USER_PARAM);
        UserPlate userPlate = (UserPlate) executionContext.getResponse().get(GetUserParamConstants.USER_PLATE_PARAM);
        UserAddress userAddress = (UserAddress) executionContext.getResponse().get(GetUserParamConstants.USER_ADDRESS_PARAM);
        UserCar userCar = (UserCar) executionContext.getResponse().get(GetUserParamConstants.USER_CAR_PARAM);
        executionContext.getResponse().put(GetUserParamConstants.USER_RESULT_PARAM, buildUser(user, userPlate, userAddress, userCar));
    }


    private UserResponse buildUser(User user, UserPlate userPlate, UserAddress userAddress, UserCar userCar) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getId());
        userResponse.setUserName(user.getName());
        userResponse.setPlate(userPlate.getPlate());
        userResponse.setAddress(userAddress.getAddress());
        userResponse.setPhoneNum(userAddress.getPhoneNum());
        userResponse.setModel(userCar.getModel());
        userResponse.setVehicle(userCar.getVehicle());
        return userResponse;
    }
}
