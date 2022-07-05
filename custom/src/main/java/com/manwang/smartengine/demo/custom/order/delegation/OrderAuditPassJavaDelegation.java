package com.manwang.smartengine.demo.custom.order.delegation;

import com.alibaba.smart.framework.engine.context.ExecutionContext;
import com.alibaba.smart.framework.engine.delegation.JavaDelegation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderAuditPassJavaDelegation implements JavaDelegation {
    @Override
    public void execute(ExecutionContext executionContext) {
        log.info("审核通过");
    }
}
