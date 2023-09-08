package com.example.biubiu.net.tcp;

import com.example.biubiu.domain.User;

public class UserClient {
    public User user;
    public String ip;
    public int characterid = 0;   //角色类型
    public int weapenid = 0;    //武器类型
    //public int port;

    public UserClient(){
        user = null;
        ip = null;
    }

    public UserClient(User user, String ip) {
        this.user = user;
        this.ip = ip;
    }
}
