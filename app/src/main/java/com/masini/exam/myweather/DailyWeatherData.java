package com.masini.exam.myweather;

import java.util.Date;

public class DailyWeatherData {

    private double temperature;
    private String icon, weatherName;
    private int weatherID;
    private Date date;

    public DailyWeatherData() { }

    public DailyWeatherData(Double temperature, String icon, String weatherName, int weatherID) {
        this.temperature = temperature;
        this.icon = icon;
        this.weatherName = weatherName;
        this.weatherID = weatherID;
    }

    public String getTemperature() {
        return temperature + "°C";
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWeatherName() {
        return weatherName;
    }

    public void setWeatherName(String weatherName) {
        this.weatherName = weatherName;
    }

    public int getWeatherID() {
        return weatherID;
    }

    public void setWeatherID(int weatherID) {
        this.weatherID = weatherID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
