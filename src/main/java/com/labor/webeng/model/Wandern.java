package com.labor.webeng.model;

import lombok.Data;

@Data 
public class Wandern {
    private boolean danger;
    private int lowestTemp;
    private int highestTemp;
    private int windSpeed;
}
