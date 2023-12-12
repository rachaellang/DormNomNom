package com.cs407.dormnomnom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

        Float caloriesSum = sharedPreferences.getFloat("caloriesSum", 0.0F);
        Float fatSum = sharedPreferences.getFloat("fatSum", 0.0F);
        Float carbsSum = sharedPreferences.getFloat("carbsSum", 0.0F);
        Float sodiumSum = sharedPreferences.getFloat("sodiumSum", 0.0F);
        Float proteinSum = sharedPreferences.getFloat("proteinSum", 0.0F);

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

            caloriesSum += (float) selectedFoodItem.getNutrition()[0];
            fatSum += (float) selectedFoodItem.getNutrition()[1];
            carbsSum += (float) selectedFoodItem.getNutrition()[2];
            sodiumSum += (float) selectedFoodItem.getNutrition()[3];
            proteinSum += (float) selectedFoodItem.getNutrition()[4];

            sharedPreferences.edit().putFloat("caloriesSum", caloriesSum).apply();
            sharedPreferences.edit().putFloat("fatSum", fatSum).apply();
            sharedPreferences.edit().putFloat("carbsSum", carbsSum).apply();
            sharedPreferences.edit().putFloat("sodiumSum", sodiumSum).apply();
            sharedPreferences.edit().putFloat("proteinSum", proteinSum).apply();

            Log.d("caloriesSum", String.valueOf(sharedPreferences.getFloat("caloriesSum", 0.0F)));
            Log.d("caloriesSum", String.valueOf(sharedPreferences.getFloat("fatSum", 0.0F)));
            Log.d("caloriesSum", String.valueOf(sharedPreferences.getFloat("carbsSum", 0.0F)));
            Log.d("caloriesSum", String.valueOf(sharedPreferences.getFloat("sodiumSum", 0.0F)));
            Log.d("caloriesSum", String.valueOf(sharedPreferences.getFloat("proteinSum", 0.0F)));

            // display macros on screen
            TextView macroDisplay = findViewById(R.id.macroText);
            macroDisplay.setText("        Macronutrient Info\n\n" +
                    "   Per serving\n" +
                    "Calories: " + caloriesSum + "\n" +
                    "Fat: " + fatSum + " g\n" +
                    "Carbs: " + carbsSum + " g\n" +
                    "Sodium: " + sodiumSum + " mg\n" +
                    "Protein: " + proteinSum + " g");

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