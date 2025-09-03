package com.example.weather;

import com.example.weather.model.WeatherData;
import com.example.weather.service.WeatherService;

import java.time.LocalDate;
import java.util.List;

public class WeatherApp {
    private static final List<String> CITIES = List.of("Chisinau", "Madrid", "Kyiv", "Amsterdam");

    public static void main(String[] args) {
        String apiKey = System.getenv("WEATHER_API_KEY");
        if (apiKey == null) {
            System.err.println("Please set environment variable WEATHER_API_KEY");
            return;
        }

        WeatherService service = new WeatherService(apiKey);
        String tomorrow = LocalDate.now().plusDays(1).toString();

        // Table header
        System.out.printf("%-12s | %-12s | %-12s | %-12s | %-12s | %-15s%n",
                "City", "Date", "Min Temp (°C)", "Max Temp (°C)",
                "Humidity (%)", "Wind (kph/dir)");
        System.out.println("--------------------------------------------------------------------------------------");

        for (String city : CITIES) {
            WeatherData data = service.getForecast(city, tomorrow);
            if (data != null) {
                System.out.printf("%-12s | %-12s | %-12.1f | %-12.1f | %-12.1f | %.1f / %s%n",
                        city, tomorrow,
                        data.getMinTemp(),
                        data.getMaxTemp(),
                        data.getHumidity(),
                        data.getWindSpeed(),
                        data.getWindDir());
            } else {
                System.out.printf("%-12s | %-12s | %-12s | %-12s | %-12s | %-15s%n",
                        city, tomorrow, "N/A", "N/A", "N/A", "N/A");
            }
        }
    }
}
