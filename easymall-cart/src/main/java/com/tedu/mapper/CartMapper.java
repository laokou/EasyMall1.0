package com.tedu.mapper;

import com.tedu.pojo.Cart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartMapper {
    @Select("select * from t_cart where user_id=#{userId}")
    List<Cart> query(String userId);

    void update(Cart cart);

    Cart queryOne(Cart cart);

    void save(Cart cart);

    @Delete("delete from t_cart where product_id=#{productId} and user_id=#{userId}")
    void delete(@Param("productId") String productId, @Param("userId") String userId);

}
