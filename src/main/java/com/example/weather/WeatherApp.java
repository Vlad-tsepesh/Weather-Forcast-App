package com.example.weather;

import com.example.weather.application.port.WeatherForecastProvider;
import com.example.weather.application.port.WeatherReportWriter;
import com.example.weather.application.usecase.WeatherForecastUseCase;
import com.example.weather.domain.service.WeatherReportBuilder;
import com.example.weather.domain.service.WindAnalyzer;
import com.example.weather.infrastructure.api.WeatherApiClient;
import com.example.weather.infrastructure.config.RetrofitFactory;
import com.example.weather.infrastructure.config.WeatherConfig;
import com.example.weather.infrastructure.mapper.WeatherForecastMapper;
import com.example.weather.infrastructure.output.WeatherReportGenerator;

public class WeatherApp {
    public static void main(String[] args) {
        WeatherConfig config = new WeatherConfig();
        WeatherForecastMapper mapper = WeatherForecastMapper.INSTANCE;
        WeatherForecastProvider apiClient = new WeatherApiClient(config, RetrofitFactory.createWeatherApi(), mapper);
        WindAnalyzer windAnalyzer = new WindAnalyzer();
        WeatherReportBuilder reportBuilder = new WeatherReportBuilder(windAnalyzer);
        WeatherReportWriter reportWriter = new WeatherReportGenerator();
        WeatherForecastUseCase useCase = new WeatherForecastUseCase(apiClient, reportBuilder,reportWriter);

        String weatherReport = useCase.runForecastForTomorrow(config.getCities());
        System.out.println(weatherReport);
    }
}
