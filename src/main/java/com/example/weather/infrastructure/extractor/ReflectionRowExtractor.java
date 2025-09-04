package com.example.weather.infrastructure.extractor;

import com.example.weather.application.port.RowExtractor;
import com.example.weather.domain.annotation.Column;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ReflectionRowExtractor implements RowExtractor {
    public List<String> extractRow(Object row) {
        Class<?> clazz = row.getClass();
        return FieldUtils.getFieldsListWithAnnotation(clazz, Column.class).stream()
                .map(f -> {
                    f.setAccessible(true);
                    try {
                        Object val = f.get(row);
                        return val != null ? val.toString() : "";
                    } catch (IllegalAccessException e) {
                        return "";
                    }
                })
                .collect(Collectors.toList());
    }
}
