package com.example.weather.mapper;

import com.example.weather.model.ForecastResponse;
import com.example.weather.model.WeatherData;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface ForecastMapper {
    ForecastMapper INSTANCE = Mappers.getMapper(ForecastMapper.class);

    @Mapping(target = "minTemp", source = "day.minTemp")
    @Mapping(target = "maxTemp", source = "day.maxTemp")
    @Mapping(target = "humidity", source = "day.avgHumidity")
    @Mapping(target = "windSpeed", source = "day.maxWindKph")
    @Mapping(target = "windDir", ignore = true)

    WeatherData toWeatherData(ForecastResponse.ForecastDay forecastDay);

    @AfterMapping
    default void setWindDir(ForecastResponse.ForecastDay forecastDay,
                            @MappingTarget WeatherData.WeatherDataBuilder builder) {
        String mostFrequent = forecastDay.hour.stream()
                .collect(Collectors.groupingBy(h -> h.windDir, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        builder.windDir(mostFrequent);
    }
}
