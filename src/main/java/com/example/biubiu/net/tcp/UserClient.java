package com.example.biubiu.net.tcp;

import com.example.biubiu.domain.User;

public class UserClient {
    public User user;
    public String ip;
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
