package com.example.weather.application;

import com.example.weather.infrastructure.api.exception.WeatherApiException;
import com.example.weather.infrastructure.api.WeatherApiClient;
import com.example.weather.infrastructure.api.dto.WeatherForecastRequest;
import com.example.weather.infrastructure.api.dto.WeatherForecastResponse;
import com.example.weather.infrastructure.config.WeatherConfig;
import com.example.weather.infrastructure.mapper.WeatherForecastMapper;
import com.example.weather.domain.model.WeatherData;
import com.example.weather.infrastructure.output.WeatherReportPrinter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class WeatherForecastUseCase {

    private final WeatherApiClient apiClient;
    private final WeatherForecastMapper mapper;
    private final WeatherConfig config;


    public void runForecastForCities(WeatherReportPrinter reportPrinter) {
        List<WeatherData> results = config.getCities().stream()
                .map(city -> getForecast(
                        WeatherForecastRequest.builder()
                                .city(city)
                                .build()))
                .toList();

        reportPrinter.generateWeatherReport(config.getCities(), results);
    }

    private WeatherData getForecast(WeatherForecastRequest request) {
        var response = apiClient.fetchForecast(request);
        validateResponse(response);
        return mapper.toWeatherData(response.forecast.forecastDays.get(0));
    }

    private void validateResponse(WeatherForecastResponse response) {
        if (response == null || response.forecast == null || response.forecast.forecastDays.isEmpty()) {
            throw new WeatherApiException("Failed to fetch forecast for ");
        }
    }
}

