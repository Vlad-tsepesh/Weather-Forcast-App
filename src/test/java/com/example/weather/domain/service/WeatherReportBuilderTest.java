package com.example.weather.domain.service;

import com.example.weather.domain.model.WeatherData;
import com.example.weather.domain.model.WeatherReportRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherReportBuilderTest {

    @Mock
    private WindAnalyzer windAnalyzer;

    private WeatherReportBuilder builder;

    @BeforeEach
    void setUp() {
        builder = new WeatherReportBuilder(windAnalyzer);
    }

    @Test
    void buildRow_returnsCorrectRow() {
        WeatherData.HourInfo hour1 = WeatherData.HourInfo.builder().windDir("N").build();
        WeatherData.HourInfo hour2 = WeatherData.HourInfo.builder().windDir("NE").build();

        WeatherData.DayInfo dayInfo = WeatherData.DayInfo.builder()
                .minTemp(10.5)
                .maxTemp(20.3)
                .avgHumidity(55.0)
                .maxWindKph(15.0)
                .build();

        WeatherData.ForecastDay forecastDay = WeatherData.ForecastDay.builder()
                .date("2025-09-05")
                .day(dayInfo)
                .hours(List.of(hour1, hour2))
                .build();

        when(windAnalyzer.mostCommonDirection(forecastDay.getHours())).thenReturn("N");

        // Execute
        WeatherReportRow row = builder.buildRow("London", forecastDay);

        // Verify
        assertEquals("London", row.getCity());
        assertEquals("2025-09-05", row.getDate());
        assertEquals(10.5, row.getMinTemp());
        assertEquals(20.3, row.getMaxTemp());
        assertEquals(55.0, row.getHumidity());
        assertEquals(15.0, row.getWindSpeed());
        assertEquals("N", row.getWindDirection());

        verify(windAnalyzer).mostCommonDirection(forecastDay.getHours());
    }

    @Test
    void buildRows_returnsListOfRows() {
        WeatherData.DayInfo dayInfo1 = WeatherData.DayInfo.builder()
                .minTemp(10).maxTemp(20).avgHumidity(50).maxWindKph(12).build();
        WeatherData.DayInfo dayInfo2 = WeatherData.DayInfo.builder()
                .minTemp(11).maxTemp(21).avgHumidity(60).maxWindKph(14).build();

        WeatherData.ForecastDay forecastDay1 = WeatherData.ForecastDay.builder()
                .date("2025-09-05")
                .day(dayInfo1)
                .hours(List.of(WeatherData.HourInfo.builder().windDir("N").build()))
                .build();

        WeatherData.ForecastDay forecastDay2 = WeatherData.ForecastDay.builder()
                .date("2025-09-06")
                .day(dayInfo2)
                .hours(List.of(WeatherData.HourInfo.builder().windDir("E").build()))
                .build();

        WeatherData weatherData = WeatherData.builder()
                .forecastDays(List.of(forecastDay1, forecastDay2))
                .build();

        when(windAnalyzer.mostCommonDirection(forecastDay1.getHours())).thenReturn("N");
        when(windAnalyzer.mostCommonDirection(forecastDay2.getHours())).thenReturn("E");

        List<WeatherReportRow> rows = builder.buildRows("London", weatherData);

        assertEquals(2, rows.size());
        assertEquals("N", rows.get(0).getWindDirection());
        assertEquals("E", rows.get(1).getWindDirection());
    }
}
