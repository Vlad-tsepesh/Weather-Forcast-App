package com.example.weather.domain.model;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WeatherData {
    private final List<ForecastDay> forecastDays;

    @Getter
    @Builder
    public static class ForecastDay {
        private final String date;
        private final DayInfo day;
        private final List<HourInfo> hours;
        private final double windDir;
    }

    @Getter
    @Builder
    public static class DayInfo {
        private final double minTemp;
        private final double maxTemp;
        private final double avgHumidity;
        private final double maxWindKph;
    }

    @Getter
    @Builder
    public static class HourInfo {
        private final String windDir;
    }
}
