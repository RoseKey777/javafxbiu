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
}
