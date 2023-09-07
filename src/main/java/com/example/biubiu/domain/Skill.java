package com.example.biubiu.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Skill {
    private int id;
    private int characterid;
    private String skillname;
    private String skilldescription;
    private BigDecimal price;
}
