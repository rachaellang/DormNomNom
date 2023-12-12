package com.cs407.dormnomnom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodActivity extends AppCompatActivity {

    String hallName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Intent intent = getIntent();
        hallName = intent.getStringExtra("HALL_NAME");
        String foodItemJson = intent.getStringExtra("FOOD_ITEM_JSON");

        // handles Back button
        ImageView backButton = findViewById(R.id.backFood);
        backButton.setOnClickListener(v -> navigateToClass(new Intent (FoodActivity.this, HallActivity.class)));

        // handles My Meal button
        Button myMeal = findViewById(R.id.myMeal);
        myMeal.setOnClickListener(v -> navigateToClass(new Intent(FoodActivity.this, MealActivity.class)));

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

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void navigateToClass(Intent intent) {
        intent.putExtra("HALL_NAME", hallName);
        startActivity(intent);
        finish();
    }
}