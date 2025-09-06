package com.example.weather.application.port;

import com.example.weather.domain.marker.Reportable;

import java.util.List;

public interface WeatherReportWriter<T extends Reportable> {
    String writeReport(List<T> objects);
}
