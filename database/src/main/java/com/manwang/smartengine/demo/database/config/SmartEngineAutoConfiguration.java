package com.manwang.smartengine.demo.database.config;

import com.alibaba.smart.framework.engine.SmartEngine;
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

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

@Slf4j
@Order(LOWEST_PRECEDENCE)
@Configuration
@ConditionalOnClass(SmartEngine.class)
public class SmartEngineAutoConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    @ConditionalOnMissingBean
    public SmartEngine smartEngine() {
        // 使用引擎配置模板，对流程引擎做一些定制化的配置
        ProcessEngineConfiguration processEngineConfiguration = new DefaultProcessEngineConfiguration();
        // 配置bean实例访问服务
        processEngineConfiguration.setInstanceAccessor(new CustomInstanceAccessService());
        // 配置id生成器
        processEngineConfiguration.setIdGenerator(new SnowFlowIdGenerator());
        // 配置userTask任务分发处理器
        processEngineConfiguration.setTaskAssigneeDispatcher(new OrderTaskAssigneeDispatcher());
        // 实例化引擎
        SmartEngine smartEngine = new DefaultSmartEngine();
        smartEngine.init(processEngineConfiguration);
        // 加载流程到引擎中
        deployProcessDefinition(smartEngine);

        return smartEngine;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 使用spring容器获取bean实例
     */
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

    /**
     * 从配置文件中加载流程定义
     * @param smartEngine
     */
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
