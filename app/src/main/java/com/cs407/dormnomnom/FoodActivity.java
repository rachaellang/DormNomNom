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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Intent intent = getIntent();
        hallName = intent.getStringExtra("HALL_NAME");
        foodItemJson = intent.getStringExtra("FOOD_ITEM_JSON");
        Log.d("in FoodActivity", foodItemJson);


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
                    SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.dormnomnom", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("json", foodItemJson).apply();

//                    Intent intent = new Intent();
                    intent.putExtra("json", foodItemJson);

//                    Toast.makeText(this, "Food added to your meal!", Toast.LENGTH_SHORT).show();
                }
            });

            // handles Back button
            ImageView backButton = findViewById(R.id.backFood);
            backButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(FoodActivity.this, HallActivity.class);
                    intent2.putExtra("HALL_NAME", hallName);
                    intent2.putExtra("json", foodItemJson);
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
                    intent2.putExtra("json", foodItemJson);
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

//    private void logFoodItemDetails() {
//        if (selectedFoodItem != null) {
//            Log.i("FoodActivity", "Food Item Details: \n" +
//                    "Name: " + selectedFoodItem.getName() + "\n" +
//                    "Calories: " + selectedFoodItem.getNutrition()[0] + "\n" +
//                    "Fat: " + selectedFoodItem.getNutrition()[1] + "g\n" +
//                    "Carbs: " + selectedFoodItem.getNutrition()[2] + "g\n" +
//                    "Sodium: " + selectedFoodItem.getNutrition()[3] + "mg\n" +
//                    "Protein: " + selectedFoodItem.getNutrition()[4] + "g");
//        } else {
//            Log.w("FoodActivity", "Selected food item is null");
//        }
//    }
}