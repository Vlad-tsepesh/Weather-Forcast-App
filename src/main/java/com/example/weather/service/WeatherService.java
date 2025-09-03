package com.example.weather.service;

import com.example.weather.client.WeatherApi;
import com.example.weather.mapper.ForecastMapper;
import com.example.weather.model.ForecastResponse;
import com.example.weather.model.WeatherData;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class WeatherService {
    private static final String BASE_URL = "http://api.weatherapi.com/v1/";
    private final WeatherApi api;
    private final String apiKey;
    private final ForecastMapper mapper;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
        this.mapper = ForecastMapper.INSTANCE;
        this.api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(WeatherApi.class);
    }

    public WeatherData getForecast(String city, String date) {
        try {
            ForecastResponse response = api.getForecast(apiKey, city, date).execute().body();
            if (response == null || response.forecast == null || response.forecast.forecastDays.isEmpty())
                return null;

            var forecastDay = response.forecast.forecastDays.get(0);
            return mapper.toWeatherData(forecastDay);

        } catch (IOException e) {
            System.err.println("Error fetching forecast for " + city + ": " + e.getMessage());
            return null;
        }
    }
}
