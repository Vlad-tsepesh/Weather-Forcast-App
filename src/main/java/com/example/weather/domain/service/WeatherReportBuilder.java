package com.example.weather.domain.service;

import com.example.weather.domain.model.WeatherData;
import com.example.weather.domain.model.WeatherReportRow;

import java.util.List;

public class WeatherReportBuilder {

    private final WindAnalyzer windAnalyzer;

    public WeatherReportBuilder(WindAnalyzer windAnalyzer) {
        this.windAnalyzer = windAnalyzer;
    }

    public WeatherReportRow buildRow(String city, WeatherData.ForecastDay forecastDay) {
        return WeatherReportRow.builder()
                .city(city)
                .date(forecastDay.getDate())
                .minTemp(forecastDay.getDay().getMinTemp())
                .maxTemp(forecastDay.getDay().getMaxTemp())
                .humidity(forecastDay.getDay().getAvgHumidity())
                .windSpeed(forecastDay.getDay().getMaxWindKph())
                .windDirection(windAnalyzer.mostCommonDirection(forecastDay.getHours()))
                .build();
    }

    public List<WeatherReportRow> buildRows(String city, WeatherData weatherData) {
        return weatherData.getForecastDays().stream()
                .map(day -> buildRow(city, day))
                .toList();
    }
}
