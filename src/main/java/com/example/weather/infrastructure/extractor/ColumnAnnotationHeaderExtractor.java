package com.example.weather.infrastructure.extractor;

import com.example.weather.application.port.HeaderExtractor;
import com.example.weather.domain.annotation.Column;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.List;
import java.util.stream.Collectors;

public class ColumnAnnotationHeaderExtractor implements HeaderExtractor {

    @Override
    public List<String> extractHeaders(Class<?> clazz) {
        return FieldUtils.getFieldsListWithAnnotation(clazz, Column.class).stream()
                .map(f -> f.getAnnotation(Column.class).value())
                .collect(Collectors.toList());
    }
}
