package com.cs407.dormnomnom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodActivity extends AppCompatActivity {

    private FoodItem selectedFoodItem;
    String foodItemJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Intent intent = getIntent();
        String hallName = intent.getStringExtra("HALL_NAME");
        foodItemJson = intent.getStringExtra("FOOD_ITEM_JSON");

        // Convert JSON string to FoodItem
        try {
            JSONObject json = new JSONObject(foodItemJson);
            selectedFoodItem = new FoodItem(
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

        Button addToMyMealButton = findViewById(R.id.addToMyMeal);
        addToMyMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodToMeal();
            }
        });
    }

    private void addFoodToMeal() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.dormnomnom", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("json", foodItemJson).apply();

        Toast.makeText(this, "Food added to your meal!", Toast.LENGTH_SHORT).show();
//        logFoodItemDetails();
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