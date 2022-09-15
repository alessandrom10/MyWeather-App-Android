package com.masini.exam.myweather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class WeatherRVAdapter extends RecyclerView.Adapter<WeatherRVAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DailyWeatherData> dailyWeatherData;

    public WeatherRVAdapter(Context context, ArrayList<DailyWeatherData> dailyWeatherData) {
        this.context = context;
        this.dailyWeatherData = dailyWeatherData;
    }

    @NonNull
    @Override
    public WeatherRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherRVAdapter.ViewHolder holder, int position) {
        @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("EEEE");
        String dayOfTheWeek = formatter.format(dailyWeatherData.get(position).getDate());
        if(position == 0) {
            String temp = "Tomorrow";
            holder.dayOfTheWeek.setText(temp);
        } else {
            holder.dayOfTheWeek.setText(dayOfTheWeek);
        }
        holder.temperature.setText(dailyWeatherData.get(position).getTemperature());
        holder.weatherName.setText(dailyWeatherData.get(position).getWeatherName());
        holder.weatherIcon.setImageResource(context.getResources().getIdentifier(dailyWeatherData.get(position).getIcon(), "drawable", context.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return dailyWeatherData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dayOfTheWeek, temperature, weatherName;
        private ImageView weatherIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayOfTheWeek = itemView.findViewById(R.id.dayOfTheWeek);
            temperature = itemView.findViewById(R.id.temperatureText);
            weatherName = itemView.findViewById(R.id.weatherNameText);
            weatherIcon = itemView.findViewById(R.id.weatherIconPicture);
        }
    }
}
