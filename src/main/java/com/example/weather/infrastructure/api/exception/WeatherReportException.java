package com.example.weather.infrastructure.api.exception;

public class WeatherReportException extends RuntimeException {
    public WeatherReportException(String message) {
        super(message);
    }
    public WeatherReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
