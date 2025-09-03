package com.example.weather.service;

import com.example.weather.client.WeatherApi;
import com.example.weather.model.*;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.*;

public class WeatherService {
    private final WeatherApi api;
    private final String apiKey;
    private static final String BASE_URL = "http://api.weatherapi.com/v1/";

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
        this.api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(WeatherApi.class);
    }

    public WeatherData getForecast(String city, String date) {
        try {
            ForecastResponse response = api.getForecast(apiKey, city, date).execute().body();
            if (response == null || response.forecast == null || response.forecast.forecastday.isEmpty())
                return null;

            ForecastResponse.ForecastDay fd = response.forecast.forecastday.get(0);
            double minTemp = fd.day.mintemp_c;
            double maxTemp = fd.day.maxTemp;
            double humidity = fd.day.avghumidity;
            double windSpeed = fd.day.maxwind_kph;

            // most common wind direction
            Map<String, Integer> freq = new HashMap<>();
            for (ForecastResponse.HourInfo h : fd.hour) {
                freq.put(h.wind_dir, freq.getOrDefault(h.wind_dir, 0) + 1);
            }
            String windDir = freq.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("N/A");

            return new WeatherData(minTemp, maxTemp, humidity, windSpeed, windDir);

        } catch (IOException e) {
            System.err.println("Error fetching forecast for " + city + ": " + e.getMessage());
            return null;
        }
    }
}
