package com.masini.exam.myweather;

import com.google.android.gms.maps.model.LatLng;

public class FavouriteLocation {
    private String commonName;
    private LatLng location;

    public FavouriteLocation(String commonName, LatLng location) {
        this.commonName = commonName;
        this.location = location;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
