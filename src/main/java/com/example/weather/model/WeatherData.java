package com.example.weather.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class WeatherData {
    private final double minTemp;
    private final double maxTemp;
    private final double humidity;
    private final double windSpeed;
    private final String windDir;
}
