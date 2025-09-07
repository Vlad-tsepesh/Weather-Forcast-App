package com.example.weather.infrastructure.config;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeatherConfigTest {

    @Test
    void getCities_returnsListOfCities() {
        WeatherConfig config = new WeatherConfig();
        List<String> cities = config.getCities();

        assertNotNull(cities);
        assertEquals(3, cities.size());
        assertTrue(cities.contains("London"));
        assertTrue(cities.contains("Paris"));
        assertTrue(cities.contains("Berlin"));
    }

    @Test
    void getApiKey_returnsCorrectKey() {
        WeatherConfig config = new WeatherConfig();
        String apiKey = config.getApiKey();

        assertNotNull(apiKey);
        assertEquals("testkey123", apiKey);
    }

    @Test
    void missingConfigFile_throwsRuntimeException() {
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {

            new WeatherConfig() {
                @Override
                public InputStream getResourceAsStream() {
                    return null;
                }
            };
        });

        assertTrue(ex.getMessage().contains("Config file not found"));
    }
}
