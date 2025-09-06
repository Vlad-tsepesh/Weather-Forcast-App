package com.example.weather.infrastructure.output;

import com.example.weather.domain.model.WeatherReportRow;
import com.example.weather.infrastructure.api.exception.WeatherReportException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeatherReportGeneratorTest {


    private WeatherReportGenerator<WeatherReportRow> generator;

    @BeforeEach
    void setUp() {
        generator = new WeatherReportGenerator<>();
    }

    @Test
    void writeReport_validObjects_returnsAsciiTable() {
        List<WeatherReportRow> rows = List.of(
                WeatherReportRow.builder().city("London").date("2025-09-05").minTemp(15.5).maxTemp(22.3).build(),
                WeatherReportRow.builder().city("Paris").date("2025-09-05").minTemp(20).maxTemp(51).build()
        );

        String report = generator.writeReport(rows);

        assertNotNull(report);
        assertTrue(report.contains("City"));
        assertTrue(report.contains("Date"));
        assertTrue(report.contains("Minimum Temperature (°C)"));
        assertTrue(report.contains("Maximum Temperature (°C)"));

        assertTrue(report.contains("London"));
        assertTrue(report.contains("Paris"));
    }


    @Test
    void writeReport_emptyList_throwsException() {
        List<WeatherReportRow> emptyList = new ArrayList<>();

        WeatherReportException exception = assertThrows(
                WeatherReportException.class,
                () -> generator.writeReport(emptyList)
        );

        assertEquals("No objects to display.", exception.getMessage());
    }
}
