package com.example.weather.infrastructure.api;

import com.example.weather.infrastructure.api.exception.WeatherApiException;
import com.example.weather.infrastructure.api.dto.WeatherForecastRequest;
import com.example.weather.infrastructure.api.dto.WeatherForecastResponse;
import com.example.weather.infrastructure.config.WeatherConfig;
import lombok.RequiredArgsConstructor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

@RequiredArgsConstructor
public class WeatherApiClient {

    private final static String BASE_URL = "http://api.weatherapi.com/v1/";
    private final WeatherConfig config;
    private final WeatherApi api = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi.class);

    public WeatherForecastResponse fetchForecast(WeatherForecastRequest request) {
        try {
            return api.getForecast(config.getApiKey(), request.getCity(), request.getDate())
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new WeatherApiException("Failed to fetch forecast for " + request.getCity(), e);
        }
    }
}

