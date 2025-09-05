package com.example.weather.application.port;

import java.util.List;

public interface WeatherReportWriter {
    String writeReport(List<?> reportRows);
}
