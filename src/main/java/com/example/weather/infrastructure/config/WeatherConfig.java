package com.example.weather.infrastructure.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class WeatherConfig {
    private static final String CONFIG_FILE = "config.properties";
    private final Properties props = new Properties();

    public WeatherConfig() {
        try (InputStream input = getResourceAsStream()) {
            if (input == null) {
                throw new RuntimeException("Config file not found: " + CONFIG_FILE);
            }
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config file", e);
        }
    }

    protected InputStream getResourceAsStream() {
        return getClass().getClassLoader().getResourceAsStream(WeatherConfig.CONFIG_FILE);
    }

    public List<String> getCities() {
        String cities = props.getProperty("cities");
        return Arrays.asList(cities.split(","));
    }

    public String getApiKey() {
        return props.getProperty("WEATHER_API_KEY");
    }
}
