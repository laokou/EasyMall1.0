package com.tedu.service;

import com.tedu.mapper.OrderMapper;
import com.tedu.vo.SysResult;
import com.tedu.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public SysResult saveOrder(Order order){

        return null;
    }


}
