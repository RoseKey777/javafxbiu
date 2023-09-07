package com.example.biubiu.domain;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Match {
    private int id;
    private int mapid;
    private int winnerid;
    private Timestamp endtime;
}
