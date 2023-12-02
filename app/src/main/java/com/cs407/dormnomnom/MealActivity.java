package com.cs407.dormnomnom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        // home button handler
        ImageView homeIcon = findViewById(R.id.homeIcon);
        homeIcon.setOnClickListener(v -> navigateToDiningActivity());

        // back button handler
        ImageView backMeal = findViewById(R.id.backMeal);
        backMeal.setOnClickListener(v -> navigateToHallActivity());

        // Persistent Storage
        SharedPreferences sharedPreferences =
                getSharedPreferences("com.cs407.dormnomnom", Context.MODE_PRIVATE);
    }

    private void navigateToDiningActivity() {
        Intent intent = new Intent(MealActivity.this, DiningActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Guides user back to the Hall Activity they were just on
     */
    private void navigateToHallActivity() {
        Intent intent = new Intent(MealActivity.this, HallActivity.class);
        startActivity(intent);
        finish();
    }
}
