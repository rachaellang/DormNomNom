package com.cs407.dormnomnom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        ImageView homeIcon = findViewById(R.id.homeIcon);
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDiningActivity();
            }
        });
    }

    private void navigateToDiningActivity() {
        Intent intent = new Intent(MealActivity.this, DiningActivity.class);
        startActivity(intent);
        finish();
    }
}
