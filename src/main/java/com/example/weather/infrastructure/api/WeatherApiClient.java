package com.example.weather.infrastructure.api;

import com.example.weather.domain.model.WeatherData;
import com.example.weather.application.port.WeatherForecastProvider;
import com.example.weather.infrastructure.api.dto.WeatherForecastResponse;
import com.example.weather.infrastructure.api.exception.NoForecastAvailableException;
import com.example.weather.infrastructure.api.exception.WeatherApiException;
import com.example.weather.infrastructure.config.WeatherConfig;
import com.example.weather.infrastructure.mapper.WeatherForecastMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class WeatherApiClient implements WeatherForecastProvider {

    private final WeatherConfig config;
    private final WeatherApi api;
    private final WeatherForecastMapper mapper;

    public WeatherData fetchForecast(String city, LocalDate date ) {
        Optional<WeatherForecastResponse> responseOptional = fetchRowForecast(city, date);
        var forecast = responseOptional
                .map(response -> response.forecast)
                .orElseThrow(() ->
                        new NoForecastAvailableException(city));

        return mapper.toWeatherData(forecast);
    }


    public Optional<WeatherForecastResponse> fetchRowForecast(String city, LocalDate date) {
        try {
            var responseDto = api.getForecast(config.getApiKey(), city, date.toString())
                    .execute();

            return Optional.ofNullable(responseDto.body());

        } catch (IOException e) {
            throw new WeatherApiException("Failed to fetch forecast for " + city, e);
        }
    }
}

