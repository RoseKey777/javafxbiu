package com.example.biubiu.net.tcp;


import com.example.biubiu.dao.UserDao;

import java.io.PrintWriter;
import java.util.Map;

//用于处理客户端的请求
public class RequestHandler {
    private UserDao userDao;
    public PrintWriter output;//客户端

    //登录请求
    public void login(Map<String, Object> data){
        String username = (String)(data.get("username"));
        String password = (String)(data.get("password"));
        System.out.println("登录的用户名为" + username);
        System.out.println("登录的密码为" + password);
        if(userDao.getUserByUsernameAndPassword(username, password) != null)
            output.println("登录成功");
        else
            output.println("登录失败");
    }

    //注册请求
    public void signup(Map<String, Object> data){
        String username = (String) (data.get("username"));
        String password = (String)(data.get("password"));
        System.out.println("注册的用户名为" + username);
        System.out.println("注册的密码为" + password);
        if(userDao.register(username, password))
            output.println("注册成功");
        else
            output.println("用户名已存在，请重新注册");
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
