package com.muyi.courage.order.service;


import com.muyi.courage.order.domain.Order;

public interface OrderService {

    /**
     * 创建订单
     */
    void create(Order order);
}
