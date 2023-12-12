package com.cs407.dormnomnom;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Intent intent = getIntent();
        String hallName = intent.getStringExtra("HALL_NAME");
        String foodItemJson = intent.getStringExtra("FOOD_ITEM_JSON");

        // Convert JSON string to FoodItem
        try {
            JSONObject json = new JSONObject(foodItemJson);
            FoodItem selectedFoodItem = new FoodItem(
                    json.getString("name"),
                    json.getDouble("calories"),
                    json.getDouble("gFat"),
                    json.getDouble("gCarbs"),
                    json.getDouble("mgSodium"),
                    json.getDouble("gProtein")
            );

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}