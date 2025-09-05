package com.example.weather.application.usecase;

import com.example.weather.application.port.WeatherForecastProvider;
import com.example.weather.application.port.WeatherReportWriter;
import com.example.weather.domain.model.WeatherData;
import com.example.weather.domain.model.WeatherReportRow;
import com.example.weather.domain.service.WeatherReportBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class WeatherForecastUseCaseTest {

    private WeatherForecastProvider forecastProvider;
    private WeatherReportBuilder reportBuilder;
    private WeatherReportWriter reportWriter;
    private WeatherForecastUseCase useCase;

    @BeforeEach
    void setUp() {
        forecastProvider = mock(WeatherForecastProvider.class);
        reportBuilder = mock(WeatherReportBuilder.class);
        reportWriter = mock(WeatherReportWriter.class);

        useCase = new WeatherForecastUseCase(forecastProvider, reportBuilder, reportWriter);
    }

    @Test
    void runForecastForTomorrow_returnsReport() {
        // Test data
        String city1 = "London";
        String city2 = "Paris";

        WeatherData.DayInfo dayInfo = WeatherData.DayInfo.builder()
                .minTemp(10)
                .maxTemp(20)
                .avgHumidity(50)
                .maxWindKph(15)
                .build();

        WeatherData.HourInfo hourInfo = WeatherData.HourInfo.builder()
                .windDir("N")
                .build();

        WeatherData.ForecastDay forecastDay = WeatherData.ForecastDay.builder()
                .date(LocalDate.now().plusDays(1).toString())
                .day(dayInfo)
                .hours(List.of(hourInfo))
                .build();

        WeatherData londonData = WeatherData.builder()
                .forecastDays(List.of(forecastDay))
                .build();

        WeatherData parisData = WeatherData.builder()
                .forecastDays(List.of(forecastDay))
                .build();

        // WeatherReportRows that the builder will return
        WeatherReportRow row1 = WeatherReportRow.builder()
                .city(city1)
                .date(forecastDay.getDate())
                .minTemp(dayInfo.getMinTemp())
                .maxTemp(dayInfo.getMaxTemp())
                .humidity(dayInfo.getAvgHumidity())
                .windSpeed(dayInfo.getMaxWindKph())
                .windDirection("N")
                .build();

        WeatherReportRow row2 = WeatherReportRow.builder()
                .city(city2)
                .date(forecastDay.getDate())
                .minTemp(dayInfo.getMinTemp())
                .maxTemp(dayInfo.getMaxTemp())
                .humidity(dayInfo.getAvgHumidity())
                .windSpeed(dayInfo.getMaxWindKph())
                .windDirection("N")
                .build();

        // Mock dependencies
        when(forecastProvider.fetchForecast(city1, LocalDate.now().plusDays(1))).thenReturn(londonData);
        when(forecastProvider.fetchForecast(city2, LocalDate.now().plusDays(1))).thenReturn(parisData);

        when(reportBuilder.buildRows(city1, londonData)).thenReturn(List.of(row1));
        when(reportBuilder.buildRows(city2, parisData)).thenReturn(List.of(row2));

        when(reportWriter.writeReport(List.of(row1, row2))).thenReturn("REPORT_OUTPUT");

        // Run use case
        String report = useCase.runForecastForTomorrow(List.of(city1, city2));

        // Verify results
        assertEquals("REPORT_OUTPUT", report);

        verify(forecastProvider).fetchForecast(city1, LocalDate.now().plusDays(1));
        verify(forecastProvider).fetchForecast(city2, LocalDate.now().plusDays(1));
        verify(reportBuilder).buildRows(city1, londonData);
        verify(reportBuilder).buildRows(city2, parisData);
        verify(reportWriter).writeReport(List.of(row1, row2));
    }
}
