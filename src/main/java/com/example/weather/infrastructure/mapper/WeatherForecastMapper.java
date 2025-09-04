package com.example.weather.infrastructure.mapper;

import com.example.weather.domain.model.WeatherData;
import com.example.weather.infrastructure.api.dto.WeatherForecastRequest;
import com.example.weather.infrastructure.api.dto.WeatherForecastResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface WeatherForecastMapper {
    WeatherForecastMapper INSTANCE = Mappers.getMapper(WeatherForecastMapper.class);

    @Mapping(target = "minTemp", source = "day.minTemp")
    @Mapping(target = "maxTemp", source = "day.maxTemp")
    @Mapping(target = "humidity", source = "day.avgHumidity")
    @Mapping(target = "windSpeed", source = "day.maxWindKph")
    @Mapping(target = "date", source = ".", qualifiedByName = "getDate")
    @Mapping(target = "windDir", source = ".", qualifiedByName = "getWindDir")
    WeatherData toWeatherData(WeatherForecastResponse.ForecastDay forecastDay);

    @Named("getDate")
    default String getDate(WeatherForecastResponse.ForecastDay forecastDay) {
        return forecastDay.date != null ? forecastDay.date : "N/A";
    }

    @Named("getWindDir")
    default String getWindDir(WeatherForecastResponse.ForecastDay forecastDay) {

        return forecastDay.hours.stream()
                .collect(Collectors.groupingBy(h -> h.windDir, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}
