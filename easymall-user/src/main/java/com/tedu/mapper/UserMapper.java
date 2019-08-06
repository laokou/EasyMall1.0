package com.tedu.mapper;

import com.tedu.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("select count(user_id) from t_user where user_name=#{userName}")
    int check(String userName);

    void regist(User user);

    @Select("select * from t_user where user_name=#{userName} and user_password=#{userPassword}")
    User querOne(@Param("userName") String userName, @Param("userPassword") String userPassword);
}
