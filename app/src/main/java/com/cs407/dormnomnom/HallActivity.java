package com.cs407.dormnomnom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import android.os.AsyncTask;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

public class HallActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);

        String hallName = getIntent().getStringExtra("HALL_NAME");
        String meal = getMealType();

        Calendar calendar = Calendar.getInstance();

        String year = String.valueOf(calendar.get(Calendar.YEAR));

        String month;
        int rawMonth = calendar.get(Calendar.MONTH) + 1;

        //Shenanigans ensue if month/day is not at least 2 digits
        if (rawMonth < 10) {
            month = "0";
            month += String.valueOf(rawMonth);
        } else {
            month = String.valueOf(rawMonth);
        }

        String day;
        int rawDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (rawDay < 10) {
            day = "0";
            day += String.valueOf(rawDay);
        } else {
            day = String.valueOf(rawDay);
        }

        ArrayList<String> stations = new ArrayList<>();

        String finalMonth = month;
        String finalDay = day;
        new GetRequestTask(new GetRequestTask.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<Station> output) {
                int i = 0;
                for (Station station : output){
                    Log.d("Station", station.getName());
                    stations.add(station.getName());
                }
                updateListView(stations, year, finalMonth, finalDay, meal, hallName);
            }
        }).execute(year, month, day, meal, hallName);


        TextView hallNameView = findViewById(R.id.diningHallName);

        String hallText = "";
        switch (hallName){
            case "gordon-avenue-market":
                hallText = "Gordon Avenue Market";
                break;
            case "rhetas-market":
                hallText = "Rheta's Market";
                break;
            case "lowell-market":
                hallText = "Lowell Market";
                break;
            case "lizs-market":
                hallText = "Liz's Market";
                break;
            case "four-lakes-market":
                hallText = "Four Lakes Market";
                break;
            case "carsons-market":
                hallText = "Carson's Market";
                break;
        }



        hallNameView.setText(hallText);

        Button myMealButton = findViewById(R.id.myMeal);
        myMealButton.setOnClickListener(v -> navigateToClass(new Intent(HallActivity.this, MealActivity.class)));

        ImageView backButton = findViewById(R.id.backDining);
        backButton.setOnClickListener(v -> navigateToClass(new Intent (HallActivity.this, DiningActivity.class)));
    }

    private void updateListView(ArrayList<String> stations, String year, String month, String day, String meal, String hallName) {
        String[] stationsArr = new String[stations.size()];
        for (int i = 0; i < stations.size(); i++) {
            stationsArr[i] = stations.get(i);
        }
        ListView mealList = findViewById(R.id.mealList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stationsArr);
        mealList.setAdapter(adapter);

        mealList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(HallActivity.this, StationActivity.class);
                i.putExtra("STATION", stationsArr[position]);
                i.putExtra("YEAR", year);
                i.putExtra("MONTH", month);
                i.putExtra("DAY", day);
                i.putExtra("MEAL", meal);
                i.putExtra("HALLNAME", hallName);

                navigateToClass(i);
            }
        });
    }

    private void navigateToClass(Intent intent) {
        startActivity(intent);
        finish();
    }

    private String getMealType() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);

        if (hours >= 7 && hours < 11) {
            return "breakfast";
        } else if (hours >= 11 && hours < 14) {
            return "lunch";
        } else if (hours >= 14 && hours < 21) {
            return "dinner";
        } else {
            return "breakfast";
        }
    }

}
