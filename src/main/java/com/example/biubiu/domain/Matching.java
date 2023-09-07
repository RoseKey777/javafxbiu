package com.example.biubiu.domain;

import lombok.Data;

@Data
public class Matching {
    private int id;
    private int matchid;
    private int userid;
    private int characterid;
    private int currentweapenid;
    private double currenthp;
    private double x;
    private double y;
}
