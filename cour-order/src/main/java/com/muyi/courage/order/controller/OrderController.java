package com.muyi.courage.order.controller;

import com.muyi.courage.order.domain.CommonResult;
import com.muyi.courage.order.domain.Order;
import com.muyi.courage.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    public CommonResult create(Order order) {
        orderService.create(order);
        return new CommonResult("订单创建成功!", 200);
    }
}
