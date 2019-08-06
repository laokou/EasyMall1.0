package com.tedu.controller;

import com.tedu.pojo.User;
import com.tedu.service.UserService;
import com.tedu.utils.CookieUtils;
import com.tedu.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user/manage")
public class UserController {

    @Autowired(required=false)
    private JedisCluster jedisCluster;
    @Autowired
    private UserService userService;

    @RequestMapping("/checkUserName")
    public SysResult checkUserName(String userName){
        if (userService.check(userName)){
            return SysResult.ok();
        }
        return SysResult.build(0,"用户名已存在",null);
    }

    @RequestMapping("/save")
    public SysResult regist(User user){
        try {
            userService.regist(user);
            return SysResult.ok();
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysResult.build(0,"注册失败",null);
    }

    @RequestMapping("/login")
    public SysResult doLogin(User user, HttpServletResponse res, HttpServletRequest req){
        String ticket=userService.doLogin(user);
        if (!StringUtils.isEmpty(ticket)){
            CookieUtils.setCookie(req,res,"EM_TICKET",ticket);
            return SysResult.ok();
        }
        return SysResult.build(0,"登录失败",null);
    }

    @RequestMapping("/query/{ticket}")
    public SysResult ticket(@PathVariable("ticket") String ticket){
        System.out.println(ticket);
       String tickets= userService.checkTicket(ticket);
       if (StringUtils.isEmpty(tickets)){
           return SysResult.build(0,"",null);
       }
        System.out.println(tickets);
       return SysResult.build(200,"ok",tickets);
    }
    /**
     * 登出
     * @param res
     * @param req
     * @return
     */
    @RequestMapping("/logout")
    public SysResult logout(HttpServletResponse res, HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        if (cookies!=null){
            String em_ticket = CookieUtils.getCookieValue(req, "EM_TICKET");
            System.out.println(em_ticket);
            if (!StringUtils.isEmpty(em_ticket)){
                Cookie cookie1=new Cookie("EM_TICKET",null);
                cookie1.setMaxAge(0);
                cookie1.setPath("/");
                res.addCookie(cookie1);
                jedisCluster.del(em_ticket);
                return SysResult.ok();
            }
        }
        return SysResult.build(0,"",null);
    }
}
