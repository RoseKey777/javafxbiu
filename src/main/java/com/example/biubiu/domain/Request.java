package com.example.biubiu.domain;

import com.alibaba.fastjson.JSON;

public class Request {
    public String type; //请求类型，如"login", "signup"
    public String data; //请求数据，为JSON格式的字符串如"{"username": "123", "password": "123"}"

    public Request(String type, Object data/*可以是一个具体对象如User对象或Map<String, Object>键值对*/){
        this.type = type;
        System.out.println(data);
        this.data = JSON.toJSONString(data);
    }

}
