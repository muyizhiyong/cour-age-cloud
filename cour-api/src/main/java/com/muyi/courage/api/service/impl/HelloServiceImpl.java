package com.muyi.courage.api.service.impl;

import com.muyi.courage.api.service.HelloService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author 杨志勇
 * @date 2021-04-28 13:51
 */

@DubboService
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello() {
        return "Hello myWord!";
    }
}
