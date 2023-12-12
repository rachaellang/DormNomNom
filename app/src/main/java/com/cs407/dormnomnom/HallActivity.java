package com.cs407.dormnomnom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class HallActivity extends AppCompatActivity {

    ExpandableListView stationList;
    String[] stationNames;
    String[] foodNames;
    HashMap<String, List<FoodItem>> stationFoodMap;
    String hallNameRaw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);
        stationList = findViewById(R.id.stationList);

        hallNameRaw = getIntent().getStringExtra("HALL_NAME");
        String hallName = "";

        if (hallNameRaw.equals("four-lakes-market")) {
            hallName = "Four Lakes";
        } else if (hallNameRaw.equals("carsons-market")) {
            hallName = "Carson's";
        } else if (hallNameRaw.equals("lizs-market")) {
            hallName = "Liz's";
        } else if (hallNameRaw.equals("gordon-avenue-market")) {
            hallName = "Gordon";
        } else if (hallNameRaw.equals("rhetas-market")) {
            hallName = "Rheta's";
        } else if (hallNameRaw.equals("lowell-market")) {
            hallName = "Lowell";
        }

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
                // Initialize the HashMap to store station names and corresponding food items
                stationFoodMap = new HashMap<>();
                stationNames = new String[output.size()];

                int i = 0;
                for (Station station : output) {
                    Log.d("Station", station.getName());
                    stationNames[i] = station.getName();
                    List<FoodItem> foodItems = new ArrayList<>(station.menuItems.size());

                    for (FoodItem food : station.menuItems) {
                        Log.d("Food item", food.getName());
                        foodItems.add(food);
                    }

                    stationFoodMap.put(stationNames[i], foodItems);
                    i++;
                }

                // Create an adapter for the ExpandableListView
                ExpandableListAdapter adapter = new CustomExpandableAdapter(HallActivity.this, stationNames, stationFoodMap);
                stationList.setAdapter(adapter);

                // takes user to FoodActivity when subitem is selected
                stationList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        // Get the selected food item
                        FoodItem selectedFoodItem = (FoodItem) adapter.getChild(groupPosition, childPosition);

                        // Convert FoodItem to JSON string
                        String foodItemJson = selectedFoodItem.toJsonString();

                        // Create an Intent to start the FoodActivity
                        Intent intent = new Intent(HallActivity.this, FoodActivity.class);
                        // intent.putExtra("HALL_NAME", hallNameRaw);
                        intent.putExtra("FOOD_ITEM_JSON", foodItemJson);
                        navigateToClass(intent);

                        return true; // Return true to indicate that the click has been handled
                    }
                });


            }
        }).execute(year, month, day, meal, hallNameRaw);

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
        intent.putExtra("HALL_NAME", hallNameRaw);
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
        } else if (hours >= 14 && hours < 23) {
            return "dinner";
        } else {
            return "breakfast";
        }
    }

}
