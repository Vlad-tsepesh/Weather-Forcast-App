package com.example.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {
    public Forecast forecast;

    public static class Forecast {
        public List<ForecastDay> forecastday;
    }

    public static class ForecastDay {
        @SerializedName("date")
        public String date;
        public DayInfo day;
        public List<HourInfo> hour;
    }

    public static class DayInfo {
        @SerializedName("maxtemp_c")
        public double maxTemp;
        public double mintemp_c;
        public double maxwind_kph;
        public double avghumidity;
    }

    public static class HourInfo {
        public String wind_dir;
    }

}
