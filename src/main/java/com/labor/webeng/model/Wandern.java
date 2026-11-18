package com.labor.webeng.model;

import lombok.Data;

import java.util.List;

@Data
public class Wandern {
    private String city;
    private boolean danger;
    private int lowestTemp;
    private int highestTemp;
    private int windSpeed;
    private int rainProbability;
    private String recommendation;
    private List<String> clothing;
}
