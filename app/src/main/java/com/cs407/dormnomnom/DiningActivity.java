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

        ImageView gordonImageView = findViewById(R.id.gordon);
        gordonImageView.setOnClickListener(v -> navigateToHallActivity("gordon-avenue-market"));

        ImageView rhetaImageView = findViewById((R.id.rheta));
        rhetaImageView.setOnClickListener((v -> navigateToHallActivity("rhetas-market")));

        ImageView fourLakesImageView = findViewById((R.id.fourlakes));
        fourLakesImageView.setOnClickListener((v -> navigateToHallActivity("four-lakes-market")));

        ImageView carsonsImageView = findViewById((R.id.carson));
        carsonsImageView.setOnClickListener((v -> navigateToHallActivity("carsons-market")));

        ImageView lizImageView = findViewById((R.id.liz));
        lizImageView.setOnClickListener((v -> navigateToHallActivity("lizs-market")));

        ImageView lowellImageView = findViewById((R.id.lowell));
        lowellImageView.setOnClickListener((v -> navigateToHallActivity("lowell-market")));
    }

    private void navigateToHallActivity(String hallName) {
        Intent intent = new Intent(DiningActivity.this, HallActivity.class);
        intent.putExtra("HALL_NAME", hallName);
        startActivity(intent);
        finish();
    }
}
