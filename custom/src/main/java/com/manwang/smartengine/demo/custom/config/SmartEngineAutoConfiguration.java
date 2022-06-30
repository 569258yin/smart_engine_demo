package com.manwang.smartengine.demo.custom.config;

import com.alibaba.smart.framework.engine.SmartEngine;
import com.alibaba.smart.framework.engine.configuration.ConfigurationOption;
import com.alibaba.smart.framework.engine.configuration.InstanceAccessor;
import com.alibaba.smart.framework.engine.configuration.ProcessEngineConfiguration;
import com.alibaba.smart.framework.engine.configuration.impl.DefaultProcessEngineConfiguration;
import com.alibaba.smart.framework.engine.configuration.impl.DefaultSmartEngine;
import com.alibaba.smart.framework.engine.exception.EngineException;
import com.alibaba.smart.framework.engine.service.command.RepositoryCommandService;
import com.alibaba.smart.framework.engine.util.IOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

@Slf4j
@Order(LOWEST_PRECEDENCE)
@Configuration
@ConditionalOnClass(SmartEngine.class)
public class SmartEngineAutoConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private ExecutorService useExecutor;

    @Bean
    @ConditionalOnMissingBean
    public SmartEngine constructSmartEngine() {
        ProcessEngineConfiguration processEngineConfiguration = new DefaultProcessEngineConfiguration();
        processEngineConfiguration.setInstanceAccessor(new CustomInstanceAccessService());
        // 并发网关需要实现分布式锁
        processEngineConfiguration.setLockStrategy(new JavaLockStrategyImpl());
        // 并发线程池配置
        processEngineConfiguration.setExecutorService((ExecutorService) applicationContext.getBean("defaultExecutor"));
        Map<String, ExecutorService> executorMap = new HashMap<>();
        executorMap.put("get_user_executor", (ExecutorService) applicationContext.getBean("useExecutor"));
        processEngineConfiguration.setExecutorServiceMap(executorMap);
        // 开启服务编排
        processEngineConfiguration.getOptionContainer().put(ConfigurationOption.SERVICE_ORCHESTRATION_OPTION);

        SmartEngine smartEngine = new DefaultSmartEngine();
        smartEngine.init(processEngineConfiguration);

        deployProcessDefinition(smartEngine);

        return smartEngine;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private class CustomInstanceAccessService implements InstanceAccessor {
        @Override
        public Object access(String name) {
            if (StringUtils.isBlank(name)) {
                return null;
            }
            // 指定的包名
            if (StringUtils.contains(name, '.')) {
                try {
                    return applicationContext.getBean(Class.forName(name));
                } catch (ClassNotFoundException e) {
                    log.error("{}加载失败", name);
                    throw new RuntimeException("类加载失败");
                }
            }
            // 指定的bean名称
            return applicationContext.getBean(name);
        }

    }


    private void deployProcessDefinition(SmartEngine smartEngine) {
        RepositoryCommandService repositoryCommandService = smartEngine
                .getRepositoryCommandService();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:/smart-engine/*.xml");
            for (Resource resource : resources) {
                InputStream inputStream = resource.getInputStream();
                repositoryCommandService.deploy(inputStream);
                IOUtil.closeQuietly(inputStream);
            }
        } catch (Exception e) {
            throw new EngineException(e);
        }

    }
}
