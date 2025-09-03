package com.example.weather.model;

public class WeatherData {
    private final double minTemp;
    private final double maxTemp;
    private final double humidity;
    private final double windSpeed;
    private final String windDir;

    public WeatherData(double minTemp, double maxTemp, double humidity, double windSpeed, String windDir) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDir = windDir;
    }

    public double getMinTemp() { return minTemp; }
    public double getMaxTemp() { return maxTemp; }
    public double getHumidity() { return humidity; }
    public double getWindSpeed() { return windSpeed; }
    public String getWindDir() { return windDir; }
}
