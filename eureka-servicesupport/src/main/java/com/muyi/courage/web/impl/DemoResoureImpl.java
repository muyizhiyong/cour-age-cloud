package com.muyi.courage.web.impl;

import com.muyi.courage.web.DemoResoure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杨志勇
 * @date 2021-03-22 14:59
 */
@Slf4j
@RestController
public class DemoResoureImpl implements DemoResoure {
    @Override
    public String helloWorld(String s) {
        log.info("sss");
        return "HelloWorld!";
    }
}
