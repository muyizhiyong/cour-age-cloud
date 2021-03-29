package com.muyi.courage.controller;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 杨志勇
 * @date 2021-03-26 13:59
 */

public interface ConsumerController {
    String PREFIX = "/helloWorld";

    @GetMapping(path = PREFIX+"/1")
    String helloWorld1(String s);

    @GetMapping(path = PREFIX+"/2")
    String helloWorld2(String s);

    @GetMapping(path = PREFIX+"/3")
    String helloWorld3(String s);
}
