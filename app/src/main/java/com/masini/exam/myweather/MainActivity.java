package com.masini.exam.myweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private final String weatherUrl = "https://api.openweathermap.org/data/3.0/onecall";
    private final String geocodeUrl = "https://maps.googleapis.com/maps/api/geocode/json";
    private final String weatherKey = "***REMOVED***";
    private final String geocodeKey = "AIzaSyD9GQ5c9iMqc2Ld1paAxK_K-xhmHKmtsBg";

    private final long MIN_TIME = 5000;
    private final long MIN_DISTANCE = 1000;
    private final int REQUEST_CODE = 101;

    private String location_provider = LocationManager.GPS_PROVIDER;

    private TextView cityName, weatherState, temperature;
    private ImageView weatherImage;

    private Button cityFinderButton;

    private LocationManager locationManager;
    private LocationListener locationListener;

    private RecyclerView weatherRV;

    private WeatherRVAdapter weatherRVAdapter;

    private void updateDataFromAPI(RequestParams requestParams) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(weatherUrl, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                LocalData.setCurrentWeatherData(new WeatherData(response));
                weatherRVAdapter = new WeatherRVAdapter(getApplicationContext(), LocalData.getCurrentWeatherData().getDailyWeathers());
                weatherRV.setAdapter(weatherRVAdapter);
                updateUI();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                throwable.printStackTrace();
            }
        });
    }

    private void updateUI() {
        if(LocalData.getCurrentWeatherData() != null) {
            temperature.setText(LocalData.getCurrentWeatherData().getTemperature());
            cityName.setText(LocalData.getDisplayingCityName());
            weatherState.setText(LocalData.getCurrentWeatherData().getWeatherName());
            int resourceID = getResources().getIdentifier(LocalData.getCurrentWeatherData().getWeatherIcon(), "drawable", getPackageName());
            weatherImage.setImageResource(resourceID);
        }
    }

    private void getCurrentWeather() {
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                /*RequestParams params = new RequestParams();
                params.put("latlng", latitude + "," + longitude);
                params.put("key", geocodeKey);
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(geocodeUrl, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        String cityName = "";
                        try {
                            cityName = response.getJSONArray("results").getJSONObject(0).getJSONArray("address_components").getJSONObject(2).getString("long_name");
                            Log.v("MyApp", "CURRENT CITY NAME IS " + cityName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        LocalData.setCurrentCityName(cityName);
                        LocalData.setDisplayingCityName(cityName);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.v("MyApp", "current url is " + getRequestURI());
                        Log.v("MyApp", "Error response is " + errorResponse);
                    }
                });*/

                latitude = 44.801534;
                longitude = 10.326942;
                LocalData.setCurrentCityName("Parma");
                LocalData.setDisplayingCityName("Parma");
                LatLng temp = new LatLng(latitude, longitude);
                LocalData.setDisplayingLocation(temp);

                getWeatherByCoordinates(latitude, longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(@NonNull String provider) { }

            @Override
            public void onProviderDisabled(@NonNull String provider) { }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        locationManager.requestLocationUpdates(location_provider, MIN_TIME, MIN_DISTANCE, locationListener);
    }

    private void getWeatherByCoordinates(double lat, double lon) {
        RequestParams params = new RequestParams();
        params.put("lat", lat);
        params.put("lon", lon);
        params.put("appid", weatherKey);
        updateDataFromAPI(params);
    }

    private void getWeatherByCityName(String cityName) {
        /*Geocoder geocoder = new Geocoder(getApplicationContext());
        List<Address> list1 = null;
        try {
            Log.v("MyApp", "Launching geocode with " + cityName);
            list1 = geocoder.getFromLocationName(cityName, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address target = list1.get(0);

        getWeatherByCoordinates(target.getLatitude(), target.getLongitude());*/

        LocalData.setCurrentCityName("Palermo");
        LocalData.setDisplayingCityName("Palermo");
        LatLng temp = new LatLng(38.128508, 13.350121);
        LocalData.setDisplayingLocation(temp);

        getWeatherByCoordinates(38.128508, 13.350121);
    }

    private void addIfNotAlreadyThere(List<FavouriteLocation> list, FavouriteLocation favouriteLocation) {
        if(list.size() == 0) {
            list.add(favouriteLocation);
            return;
        }
        for(FavouriteLocation temp : list) {
            if(Objects.equals(favouriteLocation.getCommonName(), temp.getCommonName())) {
                return;
            }
        }
        list.add(favouriteLocation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherState = findViewById(R.id.weatherCondition);
        temperature = findViewById(R.id.temperature);
        weatherImage = findViewById(R.id.weatherIcon);
        cityFinderButton = findViewById(R.id.cityFinderButton);
        weatherRV = findViewById(R.id.idRvWeather);
        cityName = findViewById(R.id.cityName);

        addIfNotAlreadyThere(LocalData.getFavouriteCities(), new FavouriteLocation("Paris", new LatLng(48.849562, 2.325739)));
        addIfNotAlreadyThere(LocalData.getFavouriteCities(), new FavouriteLocation("New York", new LatLng(40.678065, -73.987274)));
        addIfNotAlreadyThere(LocalData.getFavouriteCities(), new FavouriteLocation("Rio", new LatLng(-22.888243, -43.510703)));
        addIfNotAlreadyThere(LocalData.getFavouriteCities(), new FavouriteLocation("Beijing", new LatLng(39.978448, 116.335740)));

        cityFinderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CityFinderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String cityName = intent.getStringExtra("cityName");

        if(cityName != null) {
            LocalData.setDisplayingCityName(cityName);
            getWeatherByCityName(LocalData.getDisplayingCityName());
        } else {
            getCurrentWeather();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Location obtained succesfully", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(MainActivity.this, "Error, the user denied location's permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.addAsFavouriteButton) {
            if(LocalData.getDisplayingCityName() != null) {
                FavouriteLocation temp = new FavouriteLocation(LocalData.getDisplayingCityName(), LocalData.getDisplayingLocation());
                addIfNotAlreadyThere(LocalData.getFavouriteCities(), temp);
                Toast.makeText(MainActivity.this, "This city has been added to favourites", Toast.LENGTH_LONG).show();
            }
        } else if(item.getItemId() == R.id.listFavouriteButton) {
            Intent intent = new Intent(MainActivity.this, FavouriteListActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.showMapButton) {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}