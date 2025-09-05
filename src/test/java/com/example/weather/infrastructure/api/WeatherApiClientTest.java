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

@ExtendWith(MockitoExtension.class)
class WeatherApiClientTest {

    @Mock
    private WeatherConfig config;

    @Mock
    private WeatherApi api;

    @Mock
    private WeatherForecastMapper mapper;

    @InjectMocks
    private WeatherApiClient client;

    private String city;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        city = "London";
        date = LocalDate.now();
    }

    @Test
    void fetchForecast_success_returnsMappedWeatherData() throws IOException {
        // Prepare DTOs
        WeatherForecastResponse.Forecast forecastDto = new WeatherForecastResponse.Forecast();
        WeatherForecastResponse responseDto = new WeatherForecastResponse();
        responseDto.forecast = forecastDto;

        // Mock dependencies
        when(config.getApiKey()).thenReturn("test-key");
        Call<WeatherForecastResponse> call = mock(Call.class);
        when(api.getForecast("test-key", city, date.toString())).thenReturn(call);
        when(call.execute()).thenReturn(Response.success(responseDto));

        WeatherData mapped = mock(WeatherData.class);
        when(mapper.toWeatherData(forecastDto)).thenReturn(mapped);

        // Execute
        WeatherData result = client.fetchForecast(city, date);

        // Verify
        assertSame(mapped, result);
        verify(api).getForecast("test-key", city, date.toString());
        verify(mapper).toWeatherData(forecastDto);
    }

    @Test
    void fetchForecast_noForecast_throwsNoForecastAvailableException() throws IOException {
        Call<WeatherForecastResponse> call = mock(Call.class);

        when(config.getApiKey()).thenReturn("test-key");
        when(api.getForecast("test-key", city, date.toString())).thenReturn(call);
        when(call.execute()).thenReturn(Response.success(null));

        assertThrows(NoForecastAvailableException.class, () -> client.fetchForecast(city, date));

        verify(api).getForecast("test-key", city, date.toString());
    }

    @Test
    void fetchRowForecast_success_returnsOptional() throws IOException {
        WeatherForecastResponse responseDto = new WeatherForecastResponse();
        Call<WeatherForecastResponse> call = mock(Call.class);

        when(config.getApiKey()).thenReturn("test-key");
        when(api.getForecast("test-key", city, date.toString())).thenReturn(call);
        when(call.execute()).thenReturn(Response.success(responseDto));

        Optional<WeatherForecastResponse> result = client.fetchRowForecast(city, date);

        assertTrue(result.isPresent());
        assertSame(responseDto, result.get());

        verify(api).getForecast("test-key", city, date.toString());
    }

    @Test
    void fetchRowForecast_ioException_throwsWeatherApiException() throws IOException {
        Call<WeatherForecastResponse> call = mock(Call.class);

        when(config.getApiKey()).thenReturn("test-key");
        when(api.getForecast("test-key", city, date.toString())).thenReturn(call);
        when(call.execute()).thenThrow(new IOException("Network error"));

        WeatherApiException ex = assertThrows(WeatherApiException.class,
                () -> client.fetchRowForecast(city, date));

        assertTrue(ex.getMessage().contains("Failed to fetch forecast"));
        verify(api).getForecast("test-key", city, date.toString());
    }
}
