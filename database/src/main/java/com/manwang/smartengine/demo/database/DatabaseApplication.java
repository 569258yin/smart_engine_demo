package com.manwang.smartengine.demo.database;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = {"com.alibaba.smart.framework.engine.persister.database"})
@ComponentScan(basePackages = {"com.alibaba.smart.framework.engine.persister", "com.manwang.smartengine.demo"})
@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
    }

}
