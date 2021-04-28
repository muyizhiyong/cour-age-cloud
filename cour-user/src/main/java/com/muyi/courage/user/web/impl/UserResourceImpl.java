package com.muyi.courage.user.web.impl;

import com.muyi.courage.api.service.HelloService;
import com.muyi.courage.common.dto.DTO;
import com.muyi.courage.user.dto.UserDTO;
import com.muyi.courage.user.service.UserService;
import com.muyi.courage.user.web.UserResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 杨志勇
 * @date 2021-01-13 22:22
 */
@Slf4j
@RestController
public class UserResourceImpl implements UserResource {

    @Resource
    UserService userService;

    @DubboReference
    HelloService helloService;

    @Override
    public DTO addUser(UserDTO userDTO) {
        return userService.addUser(userDTO);
    }

    @Override
    public UserDTO qryByName(String name) {
        System.out.println("+++++++++++++++++++demo for get service +++++++++");
        System.out.println(helloService.sayHello());
        return userService.qryByName(name);
    }
}
