package com.example.weather.application.port;

import java.util.List;

public interface TableRenderer {
    void render(List<String> headers, List<List<String>> rows);
}