package com.example.weather.client;

import com.example.weather.model.ForecastResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("forecast.json")
    Call<ForecastResponse> getForecast(
            @Query("key") String apiKey,
            @Query("q") String city,
            @Query("dt") String date
    );
}
