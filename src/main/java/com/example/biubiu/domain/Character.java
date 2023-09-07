package com.example.biubiu.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Character {
    private int id;
    private String charactername;
    private BigDecimal price;
    private double hp;
}
