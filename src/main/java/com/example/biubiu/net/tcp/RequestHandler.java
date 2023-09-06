package com.example.biubiu.net.tcp;


import java.io.PrintWriter;
import java.util.Map;

//用于处理客户端的请求
public class RequestHandler {
    public PrintWriter output;//客户端

    //登录请求
    public void login(Map<String, Object> data){
        String username = (String)(data.get("username"));
        String password = (String)(data.get("password"));
        System.out.println("登录的用户名为" + username);
        System.out.println("登录的密码为" + password);
        String response = "登录成功";
        output.println(response);
    }

    //注册请求
    public void signup(Map<String, Object> data){
        String username = (String) (data.get("username"));
        String password = (String)(data.get("password"));
        System.out.println("注册的用户名为" + username);
        System.out.println("注册的密码为" + password);
        String response = "注册成功";
        output.println(response);
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
