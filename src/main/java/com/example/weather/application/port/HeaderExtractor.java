package com.example.weather.application.port;

import java.util.List;

public interface HeaderExtractor {
    List<String> extractHeaders(Class<?> clazz);
}
