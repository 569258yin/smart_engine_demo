package com.manwang.smartengine.demo.custom.user.delegation;

import com.alibaba.smart.framework.engine.context.ExecutionContext;
import com.alibaba.smart.framework.engine.delegation.JavaDelegation;
import com.manwang.smartengine.demo.custom.user.constants.GetUserParamConstants;
import com.manwang.smartengine.demo.custom.user.model.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;

@Component
@Slf4j
public class GetUserAddressJavaDelegation implements JavaDelegation {

    @Override
    public void execute(ExecutionContext executionContext) {
        log.info("Task3 获取用户地址信息 request={},response={}", executionContext.getRequest(), executionContext.getResponse());
        String userId = GetUserParamConstants.getUserId(executionContext.getRequest());
        if (userId == null) {
            throw new InvalidParameterException("userId错误");
        }
        executionContext.getResponse().put(GetUserParamConstants.USER_ADDRESS_PARAM, getUserAddressById(userId));
    }

    private UserAddress getUserAddressById(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setAddress("上海市闵行区");
        userAddress.setPhoneNum("18888888888");
        return userAddress;
    }

}
