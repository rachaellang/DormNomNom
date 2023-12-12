package com.cs407.dormnomnom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MealActivity extends AppCompatActivity {

    String hallName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        hallName = getIntent().getStringExtra("HALL_NAME");

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
