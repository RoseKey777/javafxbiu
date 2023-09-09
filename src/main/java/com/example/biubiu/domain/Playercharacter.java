package com.example.biubiu.domain;

import lombok.Data;

@Data
public class Playercharacter {
    private int id;
    private String username;
    private int characterid;
    private double speed;
    private int weaponid;
    private double bulletspeed;
    private int weapon_state;
    private int character_state;
}
