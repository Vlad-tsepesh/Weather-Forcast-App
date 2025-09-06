package com.example.weather.application.usecase;

import com.example.weather.application.port.WeatherForecastProvider;
import com.example.weather.application.port.WeatherReportWriter;
import com.example.weather.domain.model.WeatherData;
import com.example.weather.domain.model.WeatherReportRow;
import com.example.weather.domain.service.WeatherReportBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherForecastUseCaseTest {

    @Mock
    private WeatherForecastProvider forecastProvider;
    @Mock
    private WeatherReportBuilder reportBuilder;
    @Mock
    private WeatherReportWriter reportWriter;
    @InjectMocks
    private WeatherForecastUseCase useCase;

    private final LocalDate date = LocalDate.now().plusDays(1);

    private String city1;
    private String city2;

    private WeatherData londonData;
    private WeatherData parisData;

    private WeatherReportRow row1;
    private WeatherReportRow row2;

    @BeforeEach
    void setUp() {
        city1 = "London";
        city2 = "Paris";

        londonData = WeatherData.builder().build();
        parisData = WeatherData.builder().build();

        row1 = WeatherReportRow.builder().build();
        row2 = WeatherReportRow.builder().build();
    }

    @Test
    void runForecastForTomorrow_returnsReport() {
        when(forecastProvider.fetchForecast(city1, date)).thenReturn(londonData);
        when(forecastProvider.fetchForecast(city2, date)).thenReturn(parisData);

        when(reportBuilder.buildRows(city1, londonData)).thenReturn(List.of(row1));
        when(reportBuilder.buildRows(city2, parisData)).thenReturn(List.of(row2));

        when(reportWriter.writeReport(List.of(row1, row2))).thenReturn("REPORT_OUTPUT");

        String report = useCase.runForecastForTomorrow(List.of(city1, city2));

        assertEquals("REPORT_OUTPUT", report);

        verify(forecastProvider).fetchForecast(city1, date);
        verify(forecastProvider).fetchForecast(city2, date);
        verify(reportBuilder).buildRows(city1, londonData);
        verify(reportBuilder).buildRows(city2, parisData);
        verify(reportWriter).writeReport(List.of(row1, row2));
    }

    @Test
    void runForecastForTomorrow_withSingleCity_returnsReport() {
        when(forecastProvider.fetchForecast(city1, date)).thenReturn(londonData);
        when(reportBuilder.buildRows(city1, londonData)).thenReturn(List.of(row1));
        when(reportWriter.writeReport(List.of(row1))).thenReturn("SINGLE_CITY_REPORT");

        String report = useCase.runForecastForTomorrow(List.of(city1));

        assertEquals("SINGLE_CITY_REPORT", report);

        verify(forecastProvider).fetchForecast(city1, date);
        verify(reportBuilder).buildRows(city1, londonData);
        verify(reportWriter).writeReport(List.of(row1));
    }

    @Test
    void runForecastForTomorrow_withEmptyCityList_returnsEmptyReport() {
        when(reportWriter.writeReport(List.of())).thenReturn("EMPTY_REPORT");

        String report = useCase.runForecastForTomorrow(List.of());

        assertEquals("EMPTY_REPORT", report);

        verifyNoInteractions(forecastProvider, reportBuilder);
        verify(reportWriter).writeReport(List.of());
    }

    @Test
    void runForecastForTomorrow_multipleRowsPerCity() {
        WeatherReportRow row3 = WeatherReportRow.builder().build();
        when(forecastProvider.fetchForecast(city1, date)).thenReturn(londonData);
        when(reportBuilder.buildRows(city1, londonData)).thenReturn(List.of(row1, row3));
        when(reportWriter.writeReport(List.of(row1, row3))).thenReturn("MULTI_ROW_REPORT");

        String report = useCase.runForecastForTomorrow(List.of(city1));

        assertEquals("MULTI_ROW_REPORT", report);

        verify(forecastProvider).fetchForecast(city1, date);
        verify(reportBuilder).buildRows(city1, londonData);
        verify(reportWriter).writeReport(List.of(row1, row3));
    }
}
