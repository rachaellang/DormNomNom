package com.cs407.dormnomnom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class DiningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);

//        TODO: currently only for Gordon, apply to all dining halls based on API data
        ImageView gordonImageView = findViewById(R.id.gordon);
        gordonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToHallActivity();
            }
        });
    }

    private void navigateToHallActivity() {
        Intent intent = new Intent(DiningActivity.this, HallActivity.class);
        startActivity(intent);
        finish();
    }
}
