package com.example.biubiu.domain;

import lombok.Data;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private double coins;
    private int score;
    private String avatar;

    public User(){

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password, double coins, int score, String avatar) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.score = score;
        this.avatar = avatar;
    }
}
