package com.example.weather.application.port;

import java.util.List;

public interface RowExtractor {
    List<String> extractRow(Object row);
}
