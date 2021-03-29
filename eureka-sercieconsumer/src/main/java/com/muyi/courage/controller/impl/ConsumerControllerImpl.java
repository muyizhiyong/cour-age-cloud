package com.muyi.courage.controller.impl;

import com.muyi.courage.controller.ConsumerController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author 杨志勇
 * @date 2021-03-26 14:00
 */
@Slf4j
@RestController
public class ConsumerControllerImpl implements ConsumerController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String helloWorld1(String s) {
        String url = "http://localhost:8701/helloWorld?s="+s;
        String retMsg = new RestTemplate().getForObject(url,String.class);
        log.info(retMsg);
        return retMsg;
    }

    @Override
    public String helloWorld2(String s) {
        ServiceInstance serviceInstance = loadBalancerClient.choose("EUREKA-SERVICE");
        String url = "http://"+serviceInstance.getHost()+":"+serviceInstance.getPort()+"/helloWorld?s="+s ;
        String retMsg = new RestTemplate().getForObject(url,String.class);
        log.info(retMsg);
        return retMsg;
    }

    @Override
    public String helloWorld3(String s) {
        String url = "http://EUREKA-SERVICE:8701/helloWorld?s="+s;
        String retMsg = restTemplate.getForObject(url,String.class);
        log.info(retMsg);
        return retMsg;
    }
}
