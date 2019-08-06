package com.tedu.controller;

import com.tedu.pojo.Cart;
import com.tedu.service.CartService;
import com.tedu.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart/manage")
public class CarController {
    @Autowired
    private CartService cartService;
    @RequestMapping("/query")
    public List<Cart> query(String userId){
        return cartService.query(userId);
}
    @RequestMapping("/save")
    public SysResult save(Cart cart){
        try {
            cartService.save(cart);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResult.build(0,"操作失败",null);
    }
    @RequestMapping("/update")
    public SysResult update(Cart cart){
        System.out.println(cart.getNum());
        try {
            cartService.update(cart);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResult.build(0,"操作失败",null);
    }
    @RequestMapping("/delete")
    public SysResult delete(String productId, String userId){
        try {
            cartService.delete(productId,userId);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResult.build(0,"操作失败",null);
    }
}
