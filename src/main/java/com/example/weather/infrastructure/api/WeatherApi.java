package com.example.weather.infrastructure.api;

import com.example.weather.infrastructure.api.dto.WeatherForecastResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("forecast.json")
    Call<WeatherForecastResponse> getForecast(
            @Query("key") String apiKey,
            @Query("q") String city,
            @Query("dt") String date
    );
}
