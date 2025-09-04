package com.example.weather.infrastructure.output;

import com.example.weather.application.port.HeaderExtractor;
import com.example.weather.application.port.RowExtractor;
import com.example.weather.application.port.TableRenderer;
import com.example.weather.application.port.WeatherReportWriter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class WeatherReportPrinter implements WeatherReportWriter {

    private final HeaderExtractor headerExtractor;
    private final RowExtractor rowExtractor;
    private final TableRenderer tableRenderer;


    public void writeReport(List<?> reportRows) {
        if (reportRows.isEmpty()) return;

        List<String> headers = extractHeaders(reportRows);
        List<List<String>> rows = extractRows(reportRows);

        tableRenderer.render(headers, rows);
    }

    private List<String> extractHeaders(List<?> reportRows) {
        return headerExtractor.extractHeaders(reportRows.get(0).getClass());
    }

    private List<List<String>> extractRows(List<?> reportRows) {
        return reportRows.stream()
                .map(rowExtractor::extractRow)
                .collect(Collectors.toList());
    }
}
