package com.masini.exam.myweather;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.masini.exam.myweather.databinding.ActivityMapBinding;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private final String weatherUrl = "https://api.openweathermap.org/data/3.0/onecall";
    private final String weatherKey = "***REMOVED***";
    private GoogleMap mMap;
    private ActivityMapBinding binding;

    private Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for(FavouriteLocation temp : LocalData.getFavouriteCities()) {
            double lat = temp.getLocation().latitude;
            double lon = temp.getLocation().longitude;

            RequestParams requestParams = new RequestParams();
            requestParams.put("lat", lat);
            requestParams.put("lon", lon);
            requestParams.put("appid", weatherKey);
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(weatherUrl, requestParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    WeatherData weatherData = new WeatherData(response);
                    MarkerOptions markerOptions = new MarkerOptions().position(temp.getLocation()).title("Marker in " + temp.getCommonName());
                    mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(weatherData.getWeatherIcon(), 100, 100))));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    throwable.printStackTrace();
                }
            });
        }

        LatLng parma = new LatLng(44.801534, 10.326942);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(parma));
    }
}