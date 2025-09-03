package com.example.weather.model;

import lombok.*;

@Getter
@Builder
public class WeatherData {
    private final double minTemp;
    private final double maxTemp;
    private final double humidity;
    private final double windSpeed;
    private final String windDir;
}
