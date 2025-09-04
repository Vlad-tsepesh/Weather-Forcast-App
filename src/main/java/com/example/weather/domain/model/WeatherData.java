package com.example.weather.domain.model;


import lombok.Builder;

import java.util.List;

@Builder
public record WeatherData(List<ForecastDay> forecastDays) {
    @Builder
        public record ForecastDay(String date, DayInfo day, List<HourInfo> hours, double windDir) {
    }

    @Builder
        public record DayInfo(double minTemp, double maxTemp, double avgHumidity, double maxWindKph) {
    }

    @Builder
        public record HourInfo(String windDir) {
    }
}
