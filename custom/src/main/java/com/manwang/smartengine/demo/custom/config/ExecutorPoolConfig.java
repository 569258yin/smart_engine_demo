package com.manwang.smartengine.demo.custom.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ExecutorPoolConfig {

    @Bean
    public ExecutorService defaultExecutor() {
        return new ThreadPoolExecutor(
                20,
                100,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1024),
                new ThreadFactoryBuilder().setNameFormat("default-poll-%d").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    @Bean
    public ExecutorService useExecutor() {
        return new ThreadPoolExecutor(
                10,
                50,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadFactoryBuilder().setNameFormat("user-poll-%d").build(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
