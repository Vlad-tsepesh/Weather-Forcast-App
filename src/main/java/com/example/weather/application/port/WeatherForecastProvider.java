package com.example.weather.application.port;

import com.example.weather.domain.model.WeatherData;

import java.time.LocalDate;

public interface WeatherForecastProvider {
    WeatherData fetchForecast(String city, LocalDate date);
}
