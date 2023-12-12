package com.cs407.dormnomnom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodActivity extends AppCompatActivity {

    String hallName;
    private FoodItem selectedFoodItem;
    String foodItemJson;
    protected boolean foodAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.dormnomnom", Context.MODE_PRIVATE);

        Intent intent = getIntent();
        hallName = intent.getStringExtra("HALL_NAME");
        foodItemJson = intent.getStringExtra("FOOD_ITEM_JSON");

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

            // handles changing Food Name textView
            TextView foodName = findViewById(R.id.foodName);
            foodName.setText(selectedFoodItem.getName());

            // displays carbs
            TextView macroDisplay = findViewById(R.id.macroDisplay);
            macroDisplay.setText("        Macronutrient Info\n\n" +
                    "   Per serving\n" +
                    "Calories: " + selectedFoodItem.getNutrition()[0] + "\n" +
                    "Fat: " + selectedFoodItem.getNutrition()[1] + " g\n" +
                    "Carbs: " + selectedFoodItem.getNutrition()[2] + " g\n" +
                    "Sodium: " + selectedFoodItem.getNutrition()[3] + " mg\n" +
                    "Protein: " + selectedFoodItem.getNutrition()[4] + " g");

            Button addToMyMealButton = findViewById(R.id.addToMyMeal);
            addToMyMealButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foodAdded = true;
                    sharedPreferences.edit().putString("json", foodItemJson).apply();

                    //intent.putExtra("json", foodItemJson);
                    //intent.putExtra("foodAdded", true);
                    showToast();
                }
            });

            // handles Back button
            ImageView backButton = findViewById(R.id.backFood);
            backButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(FoodActivity.this, HallActivity.class);
                    intent2.putExtra("HALL_NAME", hallName);
                    if (foodItemJson != null && foodAdded) {
                        intent2.putExtra("json", foodItemJson);
                        intent2.putExtra("foodAdded", true);
                    }
                    else if (foodItemJson != null && !foodAdded) {
                        intent2.putExtra("json", sharedPreferences.getString("json", null));
                        intent2.putExtra("foodAdded", false);
                    }
                    else {
                        intent2.putExtra("json", "");
                        intent2.putExtra("foodAdded", false);
                    }
                    startActivity(intent2);
                    finish();
                }
            });

            // handles My Meal button
            Button myMeal = findViewById(R.id.myMeal);
            myMeal.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(FoodActivity.this, MealActivity.class);
                    intent2.putExtra("HALL_NAME", hallName);
                    // prevent app from crashing when clicking on "My Meal" when no food items are added to meal
                    if (foodItemJson != null && foodAdded) {
                        intent2.putExtra("json", foodItemJson);
                        intent2.putExtra("foodAdded", true);
                    }
                    else if (foodItemJson != null && !foodAdded) {
                        intent2.putExtra("json", sharedPreferences.getString("json", null));
                        intent2.putExtra("foodAdded", false);
                    }
                    else {
                        intent2.putExtra("json", "");
                        intent2.putExtra("foodAdded", false);
                    }
                    startActivity(intent2);
                    finish();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void navigateToClass(Intent intent) {
        intent.putExtra("HALL_NAME", hallName);
        startActivity(intent);
        finish();
    }

    private void showToast() {
        Toast.makeText(this, "Food added to your meal!", Toast.LENGTH_SHORT).show();
    }
}