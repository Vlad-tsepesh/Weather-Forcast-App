package com.example.weather.infrastructure.api.exception;

public class NoForecastAvailableException extends RuntimeException {
    public NoForecastAvailableException(String city) {
        super("No forecast available for city: " + city);
    }
}
