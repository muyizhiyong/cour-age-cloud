package com.muyi.courage.common.config;

import org.apache.dubbo.config.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author 杨志勇
 * @date 2021-04-30 16:57
 */
@Configuration
public class DubboConfig {
    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setCheck(false);
        consumerConfig.setTimeout(40000);
        return consumerConfig;
    }
}
