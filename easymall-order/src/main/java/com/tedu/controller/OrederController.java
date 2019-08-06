package com.tedu.controller;

import com.tedu.pojo.Order;
import com.tedu.service.OrderService;
import com.tedu.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/order/manage")
public class OrederController {

    @Autowired
    private OrderService orderService;


    //生成保存订单，前端传过来的order参数信息不完整，需要补全信息
    @RequestMapping("/save")
    public SysResult saveOrder(Order order){
    order.setOrderTime(new Date(System.currentTimeMillis()));

    SysResult result =  orderService.saveOrder(order);

        return SysResult.build(201,"no",null);

    }

}
