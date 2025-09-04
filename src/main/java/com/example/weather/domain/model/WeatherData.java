package com.example.weather.domain.model;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherData {
    private final double minTemp;
    private final double maxTemp;
    private final double humidity;
    private final double windSpeed;
    private final String date;
    private final String windDir;
}
