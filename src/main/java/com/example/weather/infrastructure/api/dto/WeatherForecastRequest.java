package com.example.weather.infrastructure.api.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class WeatherForecastRequest {
    @Builder.Default
    private final String date = LocalDate.now().plusDays(1).toString();
    private final String city;
}
