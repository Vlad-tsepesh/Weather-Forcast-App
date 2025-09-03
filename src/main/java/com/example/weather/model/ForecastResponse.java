package com.example.weather.model;

import java.util.List;

public class ForecastResponse {
    public Forecast forecast;

    public static class Forecast {
        public List<ForecastDay> forecastday;
    }
}
