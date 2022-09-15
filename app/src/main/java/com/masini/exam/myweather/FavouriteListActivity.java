package com.masini.exam.myweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class FavouriteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        MyAdapter adapter;

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(this, LocalData.getFavouriteCities());
        recyclerView.setAdapter(adapter);
    }
}