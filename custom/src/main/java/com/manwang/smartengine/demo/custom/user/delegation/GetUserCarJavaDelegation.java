package com.manwang.smartengine.demo.custom.user.delegation;

import com.alibaba.smart.framework.engine.context.ExecutionContext;
import com.alibaba.smart.framework.engine.delegation.JavaDelegation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GetUserCarJavaDelegation implements JavaDelegation {

    @Override
    public void execute(ExecutionContext executionContext) {
        log.info("判断用户车型信息 request={},response={}", executionContext.getRequest(), executionContext.getResponse());
    }


}
