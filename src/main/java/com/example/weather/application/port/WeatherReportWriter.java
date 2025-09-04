package com.example.weather.application.port;

import java.util.List;

public interface WeatherReportWriter {
    void writeReport(List<?> reportRows);
}
