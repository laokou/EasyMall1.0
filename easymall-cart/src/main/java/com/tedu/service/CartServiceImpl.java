package com.tedu.service;

import com.tedu.mapper.CartMapper;
import com.tedu.pojo.Cart;
import com.tedu.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Cart> query(String userId) {
        //如果使用缓存可以易怒redis的数据结构

        if (!StringUtils.isEmpty(userId)){
            return cartMapper.query(userId);
        }else {
            return null;
        }


    }

    @Override
    public void save(Cart cart) {
        /**
         * 通过条件判断user_id product_id 判断是否存在cart
         * 不存在新增，将数据补齐
         * 存在则只是改变数量
         */
       Cart exit= cartMapper.queryOne(cart);
       if (exit==null){
           //新增
           Product product = restTemplate.getForObject("http://ITEM-ROBBIN/product/manage/item/" + cart.getProductId(), Product.class);
           cart.setProductName(product.getProductName());
           cart.setProductImage(product.getProductImgurl());
           cart.setProductPrice(product.getProductPrice());
           cartMapper.save(cart);
       }else {
           //将 num更新
           exit.setNum(exit.getNum()+cart.getNum());
           System.out.println(exit);
           cartMapper.update(exit);
       }
    }

    @Override
    public void update(Cart cart) {
        cartMapper.update(cart);
    }

    @Override
    public void delete(String productId,String userId) {
        cartMapper.delete(productId,userId);
    }
}
