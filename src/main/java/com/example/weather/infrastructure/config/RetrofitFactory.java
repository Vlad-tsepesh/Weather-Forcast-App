package com.example.weather.infrastructure.config;

import com.example.weather.infrastructure.api.WeatherApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private final static String BASE_URL = "http://api.weatherapi.com/v1/";

    public static WeatherApi createWeatherApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(WeatherApi.class);
    }
}