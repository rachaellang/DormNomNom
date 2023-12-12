package com.cs407.dormnomnom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    String hallName;
    ListView stationList;
    String[] stationNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);
        stationNames = new String[]{"Something", "is", "wrong"};
        stationList = findViewById(R.id.stationList);

        hallName = getIntent().getStringExtra("HALL_NAME");
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
        new GetRequestTask(new GetRequestTask.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<Station> output) {
                stationNames = new String[output.size()]; // initializes String[]
                int i = 0;
                for (Station station : output){
                    Log.d("Station", station.getName());
                    stationNames[i] = station.getName(); // fills in all station names
                    i++;
                }

                ArrayAdapter<String> arr; // arr of all station names to implement in ListView
                arr = new ArrayAdapter<String>(
                        HallActivity.this,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        stationNames);
                stationList.setAdapter(arr); // sets ListView stationNames

            }
        }).execute(year, month, day, meal, hallName);

        TextView hallNameView = findViewById(R.id.diningHallName);
        hallNameView.setText(hallName);

        // handles My Meal button
        Button myMealButton = findViewById(R.id.myMeal);
        myMealButton.setOnClickListener(v -> navigateToClass(new Intent(HallActivity.this, MealActivity.class)));

        // handles Back button
        ImageView backButton = findViewById(R.id.backDining);
        backButton.setOnClickListener(v -> navigateToClass(new Intent (HallActivity.this, DiningActivity.class)));
    }

    private void navigateToClass(Intent intent) {
        intent.putExtra("HALL_NAME", hallName);
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
