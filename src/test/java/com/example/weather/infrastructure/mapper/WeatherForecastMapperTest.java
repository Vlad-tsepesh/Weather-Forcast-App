package com.example.weather.infrastructure.mapper;

import com.example.weather.domain.model.WeatherData;
import com.example.weather.infrastructure.api.dto.WeatherForecastResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WeatherForecastMapperTest {

    private final WeatherForecastMapper mapper = WeatherForecastMapper.INSTANCE;

    @Test
    void toWeatherData_mapsAllFieldsCorrectly() {
        WeatherForecastResponse.HourInfo hourInfoDto = new WeatherForecastResponse.HourInfo();
        hourInfoDto.windDir = "N";

        WeatherForecastResponse.DayInfo dayInfoDto = new WeatherForecastResponse.DayInfo();
        dayInfoDto.minTemp = 10.5;
        dayInfoDto.maxTemp = 20.3;
        dayInfoDto.avgHumidity = 75.0;
        dayInfoDto.maxWindKph = 15.0;

        WeatherForecastResponse.ForecastDay forecastDayDto = new WeatherForecastResponse.ForecastDay();
        forecastDayDto.date = "2025-09-05";
        forecastDayDto.day = dayInfoDto;
        forecastDayDto.hours = List.of(hourInfoDto);

        WeatherForecastResponse.Forecast forecastDto = new WeatherForecastResponse.Forecast();
        forecastDto.forecastDays = List.of(forecastDayDto);

        WeatherForecastResponse responseDto = new WeatherForecastResponse();
        responseDto.forecast = forecastDto;

        WeatherData weatherData = mapper.toWeatherData(forecastDto);

        assertNotNull(weatherData);
        assertEquals(1, weatherData.getForecastDays().size());

        WeatherData.ForecastDay mappedDay = weatherData.getForecastDays().get(0);
        assertEquals("2025-09-05", mappedDay.getDate());

        WeatherData.DayInfo mappedDayInfo = mappedDay.getDay();
        assertEquals(10.5, mappedDayInfo.getMinTemp());
        assertEquals(20.3, mappedDayInfo.getMaxTemp());
        assertEquals(75.0, mappedDayInfo.getAvgHumidity());
        assertEquals(15.0, mappedDayInfo.getMaxWindKph());

        WeatherData.HourInfo mappedHour = mappedDay.getHours().get(0);
        assertEquals("N", mappedHour.getWindDir());
    }

}
