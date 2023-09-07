package com.example.biubiu.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private BigDecimal coins;
    private int score;

    public User(){

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(int id, String username, String password, BigDecimal coins, int score) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.coins = coins;
        this.score = score;
    }
}
