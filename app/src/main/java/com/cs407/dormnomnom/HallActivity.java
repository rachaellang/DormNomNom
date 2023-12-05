package com.cs407.dormnomnom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);

        String hallName = getIntent().getStringExtra("HALL_NAME");

        TextView hallNameView = findViewById(R.id.diningHallName);
        hallNameView.setText(hallName);

        Button myMealButton = findViewById(R.id.myMeal);
        myMealButton.setOnClickListener(v -> navigateToMealActivity());
    }

    private void navigateToMealActivity() {
        Intent intent = new Intent(HallActivity.this, MealActivity.class);
        startActivity(intent);
        finish();
    }
}
