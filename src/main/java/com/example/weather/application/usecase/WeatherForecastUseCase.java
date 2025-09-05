package com.example.weather.application.usecase;

import com.example.weather.application.port.WeatherForecastProvider;
import com.example.weather.application.port.WeatherReportWriter;
import com.example.weather.domain.model.WeatherData;
import com.example.weather.domain.model.WeatherReportRow;
import com.example.weather.domain.service.WeatherReportBuilder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class WeatherForecastUseCase {

    private final WeatherForecastProvider forecastProvider;
    private final WeatherReportBuilder reportBuilder;
    private final WeatherReportWriter reportWriter;

    public String runForecastForTomorrow(List<String> cities) {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        List<WeatherReportRow> reportRows = new ArrayList<>();

        for (String city : cities) {
            WeatherData weatherData = forecastProvider.fetchForecast(city, tomorrow);
            reportRows.addAll(reportBuilder.buildRows(city, weatherData));
        }

        return reportWriter.writeReport(reportRows);
    }

}

