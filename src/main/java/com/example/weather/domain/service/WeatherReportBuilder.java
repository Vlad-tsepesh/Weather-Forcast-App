package com.example.weather.domain.service;

import com.example.weather.domain.model.WeatherData;
import com.example.weather.domain.model.WeatherReportRow;

import java.util.List;

public class WeatherReportBuilder {

    private final WindAnalyzer windAnalyzer;

    public WeatherReportBuilder(WindAnalyzer windAnalyzer) {
        this.windAnalyzer = windAnalyzer;
    }

    public WeatherReportRow buildRow(String city, WeatherData.ForecastDay day) {
        return WeatherReportRow.builder()
                .city(city)
                .date(day.date())
                .minTemp(day.day().minTemp())
                .maxTemp(day.day().maxTemp())
                .humidity(day.day().avgHumidity())
                .windSpeed(day.day().maxWindKph())
                .windDirection(windAnalyzer.mostCommonDirection(day.hours()))
                .build();
    }

    public List<WeatherReportRow> buildRows(String city, WeatherData weatherData) {
        return weatherData.forecastDays().stream()
                .map(day -> buildRow(city, day))
                .toList();
    }
}
