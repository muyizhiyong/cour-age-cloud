package com.muyi.courage.web;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 杨志勇
 * @date 2021-03-22 14:47
 */
public interface DemoResoure {
    String PREFIX = "/helloWorld";

    @GetMapping(path = PREFIX)
    String helloWorld(String s);
}
