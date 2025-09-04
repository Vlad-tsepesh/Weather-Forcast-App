package com.example.weather.domain.service;

import com.example.weather.domain.model.WeatherData;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WindAnalyzer {

    public String mostCommonDirection(List<WeatherData.HourInfo> hours) {
        return hours.stream()
                    .collect(Collectors.groupingBy(WeatherData.HourInfo::windDir, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("N/A");
    }
}
