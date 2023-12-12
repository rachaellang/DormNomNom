package com.cs407.dormnomnom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MealActivity extends AppCompatActivity {

    String hallName;
    String foodItemJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.dormnomnom", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        hallName = intent.getStringExtra("HALL_NAME");
        foodItemJson = intent.getStringExtra("json");

        try {

            JSONObject json = new JSONObject(foodItemJson);
            Log.d("tag", "after json creation");
            FoodItem selectedFoodItem = new FoodItem(
                    json.getString("name"),
                    json.getDouble("calories"),
                    json.getDouble("gFat"),
                    json.getDouble("gCarbs"),
                    json.getDouble("mgSodium"),
                    json.getDouble("gProtein")
            );
            Log.d("tag", "after json creation2");


//            Log.d("tag", selectedFoodItem.getName());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // home button handler
        ImageView homeIcon = findViewById(R.id.homeIcon);
        homeIcon.setOnClickListener(v -> navigateToClass(new Intent(MealActivity.this, DiningActivity.class)));

        // back button handler
        ImageView backMeal = findViewById(R.id.backMeal);
        backMeal.setOnClickListener(v -> navigateToClass(new Intent(MealActivity.this, HallActivity.class)));
    }

    /**
     * Guides user back to the class that they were just on
     */
    private void navigateToClass(Intent intent) {
        intent.putExtra("HALL_NAME", hallName);
        startActivity(intent);
        finish();
    }
}
