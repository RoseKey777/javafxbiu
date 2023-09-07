package com.example.biubiu.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Matchdetail {
    private int id;
    private int matchid;
    private int userid;
    private int scorechange;
    private double coinchange;
}
