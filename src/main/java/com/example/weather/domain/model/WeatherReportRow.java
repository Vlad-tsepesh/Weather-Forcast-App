package com.example.weather.domain.model;

import com.example.weather.domain.annotation.Column;
import com.example.weather.domain.marker.Reportable;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherReportRow implements Reportable {
    @Column("City")
    private final String city;
    @Column("Date")
    private final String date;
    @Column("Minimum Temperature (°C)")
    private final double minTemp;
    @Column("Maximum Temperature (°C)")
    private final double maxTemp;
    @Column("Humidity (%)")
    private final double humidity;
    @Column("Wind Speed (kph)")
    private final double windSpeed;
    @Column("Wind Direction")
    private final String windDirection;
}
