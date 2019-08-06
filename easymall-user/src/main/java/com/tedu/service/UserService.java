package com.tedu.service;

import com.tedu.pojo.User;

public interface UserService {
    boolean check(String userName);

    void regist(User user);

    String doLogin(User user);

    String checkTicket(String ticket);
}
