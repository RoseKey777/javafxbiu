package com.example.biubiu.domain;

import com.example.biubiu.net.tcp.UserClient;

public class Room {
    public int id;
    public int num = 0;    //房间人数
    public UserClient[] userClients = new UserClient[4];   //玩家列表
}
