package com.manwang.smartengine.demo.custom.config;

import com.alibaba.smart.framework.engine.configuration.LockStrategy;
import com.alibaba.smart.framework.engine.context.ExecutionContext;
import com.alibaba.smart.framework.engine.exception.LockException;
import com.google.common.util.concurrent.Striped;

import java.util.concurrent.locks.Lock;

public class JavaLockStrategyImpl implements LockStrategy {

    private static final Striped<Lock> PROCESS_LOCK = Striped.lazyWeakLock(1024);

    @Override
    public void tryLock(String processInstanceId, ExecutionContext context) throws LockException {
        PROCESS_LOCK.get(processInstanceId).lock();
    }

    @Override
    public void unLock(String processInstanceId, ExecutionContext context) throws LockException {
        Lock lock = PROCESS_LOCK.get(processInstanceId);
        if (lock != null) {
            lock.unlock();
        }
    }
}
