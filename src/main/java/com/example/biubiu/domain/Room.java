package com.example.biubiu.domain;

import com.example.biubiu.net.tcp.UserClient;

import java.util.ArrayList;

public class Room {
    public int id;
    public int num = 0;    //房间人数
    public ArrayList<UserClient> userClients = new ArrayList<>();   //玩家列表
}
