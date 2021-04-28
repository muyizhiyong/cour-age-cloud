package com.muyi.courage.api.web;

import com.alibaba.fastjson.JSONObject;
import com.muyi.courage.api.dto.UserDTO;
import com.muyi.courage.api.service.HelloService;
import com.sun.tools.javac.util.Convert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 杨志勇
 * @date 2021-04-27 09:14
 */
@RestController
public class HelloResource {

    @Resource
    private HelloService helloService;


    @GetMapping("/hello")
    public String hello() {
        return  helloService.sayHello();
    }

    @GetMapping("/currentUser")
    public UserDTO currentUser() {
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStr = request.getHeader("user");
        JSONObject userJsonObject = JSONObject.parseObject(userStr);
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userJsonObject.getString("user_name"));
        System.out.println(userJsonObject.getString("id"));
        System.out.println(userJsonObject.get("authorities"));
        return userDTO;
    }

}
