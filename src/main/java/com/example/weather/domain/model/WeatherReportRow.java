package com.example.weather.domain.model;

import com.example.weather.domain.annotation.Column;
import lombok.Builder;

@Builder
public record WeatherReportRow(@Column("City") String city,
                               @Column("Date") String date,
                               @Column("Minimum Temperature (°C)") double minTemp,
                               @Column("Maximum Temperature (°C)") double maxTemp,
                               @Column("Humidity (%)") double humidity,
                               @Column("Wind Speed (kph)") double windSpeed,
                               @Column("Wind Direction") String windDirection) {
}
