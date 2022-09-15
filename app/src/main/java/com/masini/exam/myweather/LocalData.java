package com.masini.exam.myweather;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class LocalData {
    private static String currentCityName, displayingCityName;
    private static LatLng displayingLocation;
    private static List<FavouriteLocation> favouriteCities = new ArrayList<>();
    private static WeatherData currentWeatherData = new WeatherData();

    public static String getCurrentCityName() {
        return currentCityName;
    }

    public static void setCurrentCityName(String currentCityName) {
        LocalData.currentCityName = currentCityName;
    }

    public static String getDisplayingCityName() {
        return displayingCityName;
    }

    public static void setDisplayingCityName(String displayingCityName) {
        LocalData.displayingCityName = displayingCityName;
    }

    public static LatLng getDisplayingLocation() {
        return displayingLocation;
    }

    public static void setDisplayingLocation(LatLng displayingLocation) {
        LocalData.displayingLocation = displayingLocation;
    }

    public static List<FavouriteLocation> getFavouriteCities() {
        return favouriteCities;
    }

    public static void setFavouriteCities(List<FavouriteLocation> favouriteCities) {
        LocalData.favouriteCities = favouriteCities;
    }

    public static WeatherData getCurrentWeatherData() {
        return currentWeatherData;
    }

    public static void setCurrentWeatherData(WeatherData currentWeatherData) {
        LocalData.currentWeatherData = currentWeatherData;
    }
}
