package com.example.weather;

import com.example.weather.application.WeatherForecastUseCase;
import com.example.weather.infrastructure.api.WeatherApiClient;
import com.example.weather.infrastructure.config.WeatherConfig;
import com.example.weather.infrastructure.mapper.WeatherForecastMapper;
import com.example.weather.infrastructure.output.WeatherReportPrinter;

public class WeatherApp {
    public static void main(String[] args) {
        WeatherConfig config = new WeatherConfig();
        WeatherForecastMapper mapper = WeatherForecastMapper.INSTANCE;
        WeatherApiClient apiClient = new WeatherApiClient(config);
        WeatherForecastUseCase useCase = new WeatherForecastUseCase(apiClient, mapper, config);
        WeatherReportPrinter reportPrinter = new WeatherReportPrinter();

        useCase.runForecastForCities(reportPrinter);
    }
}
