package com.example.biubiu.net.tcp;


import com.alibaba.fastjson.JSON;
import com.example.biubiu.dao.UserDao;
import com.example.biubiu.domain.User;

import java.io.PrintWriter;
import java.util.Map;

//用于处理客户端的请求
public class RequestHandler {
    private UserDao userDao = new UserDao();
    public PrintWriter output;//客户端

    //登录请求
    public User login(Map<String, Object> data){
        String username = (String)(data.get("username"));
        String password = (String)(data.get("password"));
        System.out.println("登录的用户名为" + username);
        System.out.println("登录的密码为" + password);
        System.out.println(userDao);
        User user = userDao.getUserByUsernameAndPassword("123", "123");
        System.out.println(user);
        if(userDao.getUserByUsernameAndPassword(username, password) != null) {
            output.println("登录成功");
            return new User(username, password);
        }
        else{
            output.println("登录失败");
            return null;
        }
    }

    //注册请求
    public void signup(Map<String, Object> data){
        String username = (String)(data.get("username"));
        String password = (String)(data.get("password"));
        System.out.println("注册的用户名为" + username);
        System.out.println("注册的密码为" + password);
        if(userDao.register(username, password))
            output.println("注册成功");
        else
            output.println("用户名已存在，请重新注册");
    }

    //获取用户信息
    public void getuserinfo(Map<String, Object> data){
        String username = (String)(data.get("username"));
        User user = userDao.getUserByUsername(username);
        System.out.println(JSON.toJSONString(user));
        output.println(JSON.toJSONString(user));
    }

    //创建房间
    public void createroom(Map<String, Object> data){

    }

    //进入房间
    public void enterroom(Map<String, Object> data){

    }

    //开始游戏
    public void startgame(Map<String, Object> data){

    }
}
