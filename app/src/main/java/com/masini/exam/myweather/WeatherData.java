package com.masini.exam.myweather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherData {

    private Double temperature;
    private String weatherIcon, weatherName;
    private int weatherID;
    private ArrayList<DailyWeatherData> dailyWeathers;

    public WeatherData() { }

    public WeatherData(JSONObject jsonObject) {

        Log.v("MyApp", "Initialization started");

        try {
            JSONObject current = jsonObject.getJSONObject("current");
            weatherID = current.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherName = current.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherIcon = getWeatherIconByWeatherID(weatherID);
            temperature = (double)Math.round(current.getDouble("temp") - 273.15);
            JSONArray days = jsonObject.getJSONArray("daily");

            dailyWeathers = new ArrayList<>();

            for(int i = 1; i < days.length(); i++) {
                JSONObject currentObject = days.getJSONObject(i);
                JSONObject currentTemperature = currentObject.getJSONObject("temp");
                DailyWeatherData tempDay = new DailyWeatherData();
                tempDay.setDate(new Date((long) currentObject.getDouble("dt") * 1000));
                tempDay.setWeatherID(currentObject.getJSONArray("weather").getJSONObject(0).getInt("id"));
                tempDay.setWeatherName(currentObject.getJSONArray("weather").getJSONObject(0).getString("main"));
                tempDay.setIcon(getWeatherIconByWeatherID(tempDay.getWeatherID()));
                tempDay.setTemperature(Math.round(((currentTemperature.getDouble("max") + currentTemperature.getDouble("min")) / 2) - 273.15));
                dailyWeathers.add(tempDay);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String getWeatherIconByWeatherID(int condition) {

        if(condition >= 0 && condition < 300) {
            return "thunderstorm";
        } else if (condition >= 300 && condition < 500) {
            return "light_rain";
        } else if (condition >= 500 && condition < 600) {
            return"rainy";
        } else if (condition >= 600 && condition < 700) {
            return "snow";
        } else if (condition >= 700 && condition < 800) {
            return "fog";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition < 900) {
            return "cloud";
        }
        return "question_mark";
    }

    public String getTemperature() {
        return temperature + "°C";
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getWeatherName() { return weatherName; }

    public ArrayList<DailyWeatherData> getDailyWeathers() {
        return dailyWeathers;
    }
}
