package com.tedu.service;

import com.tedu.pojo.Cart;

import java.util.List;

public interface CartService {
    List<Cart> query(String userId);

    void save(Cart cart);

    void update(Cart cart);

    void delete(String productId, String userId);
}
