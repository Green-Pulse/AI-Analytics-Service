package com.greenpulse.ai.ai_analytics_service.dto;

import lombok.Data;

@Data
public class SensorData {
    private double temp;
    private double pres;
    private double dewp;
    private double rain;
    private double windSpeed;
    private boolean wd_NE;
    private boolean wd_NW;
    private boolean wd_SE;
}
