package com.masini.exam.myweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

public class CityFinderActivity extends AppCompatActivity {

    private Boolean alreadyLaunched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_finder);

        alreadyLaunched = false;

        final EditText editText = findViewById(R.id.citySearchBar);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                String newCity = editText.getText().toString();
                Intent intent = new Intent(CityFinderActivity.this, MainActivity.class);
                intent.putExtra("cityName", newCity);
                if(!alreadyLaunched) {
                    alreadyLaunched = true;
                    startActivity(intent);
                }
                return false;
            }
        });

    }
}