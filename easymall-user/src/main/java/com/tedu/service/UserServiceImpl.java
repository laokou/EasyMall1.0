package com.tedu.service;

import com.tedu.mapper.UserMapper;
import com.tedu.pojo.User;
import com.tedu.utils.MD5Util;
import com.tedu.utils.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
     private JedisCluster jedisCluster;

    @Override
    public boolean check(String userName) {
       if (userMapper.check(userName)==0){
           return true;
       }
        return false;
    }

    @Override
    public void regist(User user) {
        //表单不填写用户id，   采用UUID生成随机字符串作为用户id，补全用户信息
        user.setUserId(UUID.randomUUID().toString());
        //用户输入密码不应该明文存储和查看，此处采用MD5加密； 把加密后的
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        userMapper.regist(user);
    }

    @Override
    public String doLogin(User user) {
        try {
        user.setUserPassword(MD5Util.md5(user.getUserPassword()));
        //判断用户名与密码是否能匹配
       User exist= userMapper.querOne(user.getUserName(),user.getUserPassword());
       //若不可以则返回空
       if (exist==null){
           return "";
       }else {//可以则把他保存在redis缓存
           //拼接cookies名的字符串：EM_TICKET+系统时间+用户名
           String oTickets = jedisCluster.get("login_" + user.getUserName());
           if (!StringUtils.isEmpty(oTickets)){
               jedisCluster.del(oTickets);
           }
           String ticket="EM_TICKET"+System.currentTimeMillis()+user.getUserName();
            //使用方法把对象转化成jason字符串
           String userInfo = MapperUtil.MP.writeValueAsString(exist);
            //保存到redis中并且保存60*60*2秒
           String setex = jedisCluster.setex(ticket, 600, userInfo);
           System.out.println(setex);

           String setex1 = jedisCluster.setex("login_" + user.getUserName(), 60 * 60 * 2, userInfo);
           System.out.println(setex1);
           return ticket;
       }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String checkTicket(String ticket) {
        try {
            Long time = jedisCluster.pttl(ticket);
            if (time<1000*60*30){
                time=time+1000*60*30;
                jedisCluster.pexpire(ticket,time);
            }
           return jedisCluster.get(ticket);

        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }
}
