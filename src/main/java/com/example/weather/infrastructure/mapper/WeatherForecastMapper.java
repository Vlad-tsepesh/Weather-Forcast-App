package com.example.weather.infrastructure.mapper;

import com.example.weather.domain.model.WeatherData;
import com.example.weather.infrastructure.api.dto.WeatherForecastResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeatherForecastMapper {
    WeatherForecastMapper INSTANCE = Mappers.getMapper(WeatherForecastMapper.class);

    WeatherData toWeatherData(WeatherForecastResponse.Forecast forecast);
}
