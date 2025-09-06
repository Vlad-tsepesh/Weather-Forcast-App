package com.example.weather.infrastructure.output;

import com.example.weather.application.port.WeatherReportWriter;
import com.example.weather.domain.annotation.Column;
import com.example.weather.domain.marker.Reportable;
import com.example.weather.infrastructure.api.exception.WeatherReportException;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class WeatherReportGenerator<T extends Reportable> implements WeatherReportWriter<T> {

    @Override
    public String writeReport(List<T> objects) {
        validate(objects);

        Field[] fields = objects.get(0).getClass().getDeclaredFields();

        String[] headers = extractHeaders(fields);
        String[][] rows = extractRows(objects, fields);

        return renderAsciiTable(headers, rows);
    }

    private void validate(List<T> objects) {
        if (objects == null || objects.isEmpty()) {
            throw new WeatherReportException("No objects to display.");
        }

        Field[] fields = objects.get(0).getClass().getDeclaredFields();
        long realFieldsCount = Arrays.stream(fields)
                .filter(f -> !f.isSynthetic())
                .count();

        if (realFieldsCount == 0) {
            throw new WeatherReportException("No fields found to display.");
        }
    }

    private String[] extractHeaders(Field[] fields) {
        return Arrays.stream(fields)
                .map(f -> Optional.ofNullable(f.getAnnotation(Column.class))
                        .map(Column::value)
                        .orElse(f.getName()))
                .toArray(String[]::new);
    }

    private String[][] extractRows(List<T> objects, Field[] fields) {
        return objects.stream()
                .map(obj -> Arrays.stream(fields)
                        .map(f -> {
                            try {
                                Object val = FieldUtils.readField(f, obj, true);
                                return val != null ? val.toString() : "N/A";
                            } catch (IllegalAccessException e) {
                                throw new WeatherReportException(
                                        "Cannot read field " + f.getName(), e);
                            }
                        })
                        .toArray(String[]::new)
                ).toArray(String[][]::new);
    }

    private String renderAsciiTable(String[] headers, String[][] rows) {
        AsciiTable at = new AsciiTable();

        at.addRule();
        at.addRow((Object[]) headers);
        at.addRule();

        for (String[] row : rows) {
            at.addRow((Object[]) row);
            at.addRule();
        }

        at.setTextAlignment(TextAlignment.CENTER);
        at.getRenderer().setCWC(new CWC_LongestLine());
        at.setPaddingLeftRight(1);

        return at.render();
    }
}
