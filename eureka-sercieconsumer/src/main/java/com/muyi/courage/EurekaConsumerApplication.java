package com.muyi.courage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 杨志勇
 * @date 2021-03-26 13:53
 */
@SpringBootApplication
@EnableDiscoveryClient
public class EurekaConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaConsumerApplication.class,args);
    }
}
