package com.muyi.courage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 杨志勇
 * @date 2021-03-22 16:50
 */
@RestController
@RequestMapping("/Hello2")
public class Controller2 {
    @RequestMapping("/World")
    public String helloWorld(String s){
        System.out.println(s);
        return "OK";
    }


}
