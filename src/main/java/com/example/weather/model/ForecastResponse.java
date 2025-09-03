package com.example.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {
    public Forecast forecast;

    public static class Forecast {
        @SerializedName("forecastday")
        public List<ForecastDay> forecastDays;
    }

    public static class ForecastDay {
        public DayInfo day;
        public List<HourInfo> hour;
    }

    public static class DayInfo {
        @SerializedName("maxtemp_c")
        public double maxTemp;
        @SerializedName("mintemp_c")
        public double minTemp;
        @SerializedName("maxwind_kph")
        public double maxWindKph;
        @SerializedName("avghumidity")
        public double avgHumidity;
    }

    public static class HourInfo {
        @SerializedName("wind_dir")
        public String windDir;
    }

}
