package com.example.weather.infrastructure.api;

import com.example.weather.domain.model.WeatherData;
import com.example.weather.infrastructure.api.dto.WeatherForecastResponse;
import com.example.weather.infrastructure.api.exception.NoForecastAvailableException;
import com.example.weather.infrastructure.api.exception.WeatherApiException;
import com.example.weather.infrastructure.config.WeatherConfig;
import com.example.weather.infrastructure.mapper.WeatherForecastMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class WeatherApiClientTest {

    @Mock
    private WeatherConfig config;

    @Mock
    private WeatherApi api;

    @Mock
    private WeatherForecastMapper mapper;

    @Mock
    private Call<WeatherForecastResponse> call; // Retrofit call mock

    @InjectMocks
    private WeatherApiClient client;

    private String apiKey = "test-key";
    private String city = "London";
    private LocalDate date = LocalDate.now();

    private WeatherForecastResponse.Forecast forecastDto;
    private WeatherForecastResponse responseDto;
    private WeatherData mappedWeatherData;

    @BeforeEach
    void setUp() throws IOException {
        // Common DTO setup
        forecastDto = new WeatherForecastResponse.Forecast();
        responseDto = new WeatherForecastResponse();
        responseDto.forecast = forecastDto;

        mappedWeatherData = mock(WeatherData.class);

        // Mock config to always return API key
        when(config.getApiKey()).thenReturn(apiKey);

        // Mock Retrofit API call
        when(api.getForecast(anyString(), anyString(), anyString())).thenReturn(call);
        when(call.execute()).thenReturn(Response.success(responseDto));

        // Mock mapper
        when(mapper.toWeatherData(any(WeatherForecastResponse.Forecast.class))).thenReturn(mappedWeatherData);
    }

    @Test
    void fetchForecast_success_returnsMappedWeatherData() {
        WeatherData result = client.fetchForecast(city, date);

        assertSame(mappedWeatherData, result);

        // Verify interactions
        verify(config).getApiKey();
        verify(api).getForecast(apiKey, city, date.toString());
        verify(mapper).toWeatherData(forecastDto);
    }

    @Test
    void fetchForecast_noForecast_throwsNoForecastAvailableException() throws IOException {
        // Simulate API returns body null
        when(call.execute()).thenReturn(Response.success(null));

        assertThrows(NoForecastAvailableException.class, () -> client.fetchForecast(city, date));

        verify(api).getForecast(apiKey, city, date.toString());
        verify(mapper, never()).toWeatherData(any());
    }

    @Test
    void fetchRowForecast_success_returnsOptional() {
        Optional<WeatherForecastResponse> result = client.fetchRowForecast(city, date);

        assertTrue(result.isPresent());
        assertEquals(responseDto, result.get());

        verify(api).getForecast(apiKey, city, date.toString());
    }

    @Test
    void fetchRowForecast_ioException_throwsWeatherApiException() throws IOException {
        when(call.execute()).thenThrow(new IOException("Network error"));

        assertThrows(WeatherApiException.class, () -> client.fetchRowForecast(city, date));

        verify(api).getForecast(apiKey, city, date.toString());
    }
}
