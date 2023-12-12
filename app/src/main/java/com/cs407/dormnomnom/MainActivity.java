package com.cs407.dormnomnom;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import android.location.Location;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> requestLocationPermission());
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION
            );

        } else {
            locationCheck();
        }
    }

    /**
     * Compares users location with the locations of all dining halls. If the
     * user is within 100 meters of a dining hall, the name of the dining hall
     * is returned.
     *
     * @param location user's current location
     * @return null by default - name of dining hall if conditions are met
     */
    private String closeHall(Location location) {
        ArrayList<Location> hallLocations = new ArrayList<>();

        // Hardcoding all dining hall locations
        Location gordonLocation = new Location("");
        gordonLocation.setLatitude(43.071201560598574);
        gordonLocation.setLongitude(-89.39830938173728);

        hallLocations.add(gordonLocation);

        Location rhetaLocation = new Location("");
        rhetaLocation.setLatitude(43.07388750441592);
        rhetaLocation.setLongitude(-89.40171155540438);

        hallLocations.add(rhetaLocation);

        Location lowellLocation = new Location("");
        lowellLocation.setLatitude(43.076258261144005);
        lowellLocation.setLongitude(-89.39578270287092);

        hallLocations.add(lowellLocation);

        Location lizLocation = new Location("");
        lizLocation.setLatitude(43.076793151903736);
        lizLocation.setLongitude(-89.40694633157477);

        hallLocations.add(lizLocation);

        Location fourLocation = new Location("");
        fourLocation.setLongitude(43.0777065128387);
        fourLocation.setLatitude(-89.41780649623819);

        hallLocations.add(fourLocation);

        Location carsonLocation = new Location("");
        carsonLocation.setLongitude(43.0771386284858);
        carsonLocation.setLatitude(-89.41130262732818);

        hallLocations.add(carsonLocation);

        String[] hallNames = {"gordon-avenue-market", "rhetas-market", "lowell-market", "lizs-market",
                "four-lakes-market", "carsons-market"};

        // Debugging - ignore for now
        Log.d("Location", String.valueOf(location.getLongitude()));
        Log.d("Location", String.valueOf(location.getLatitude()));


        // Loop which checks if user is within 100 meters of a dining hall
        for (Location hallLocation : hallLocations) {

            // Debugging - ignore for now
            Log.d("AHHH", "Debug log message");
            Log.d("Distance", String.valueOf(location.distanceTo(hallLocation)));

            if (location.distanceTo(hallLocation) <= 500) {
                return hallNames[hallLocations.indexOf(hallLocation)];
            }
        }

        return null;
    }

    private void proceedToNextPage() {
        Intent intent = new Intent(MainActivity.this, DiningActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationCheck();
            } else {
                showLocationPermissionAlert();
            }
        }
    }

    private void showLocationPermissionAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Location Permission Required");
        builder.setMessage("To use this app, you need to grant location permission.");

        builder.setPositiveButton("OK", (dialog, which) -> requestLocationPermission());

        builder.setNegativeButton("Cancel", (dialog, which) -> Toast.makeText(MainActivity.this, "Location permission is required.", Toast.LENGTH_SHORT).show());

        builder.create().show();
    }

    /**
     * Navigates to hall activity directly if user is within specified distance of dining hall
     *
     * @param hallName The dining hall for which information will be displayed about
     */
    private void navigateToHallActivity(String hallName) {
        Intent intent = new Intent(MainActivity.this, HallActivity.class);
        intent.putExtra("HALL_NAME", hallName);
        startActivity(intent);
        finish();
    }

    /**
     * Checks if a user is currently physically close to a dining hall.
     * If a user is within a distance of a dining hall specified in the
     * locationCheck function, the user will be directed to that dining hall's
     * page automatically.
     */
    private void locationCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {

                    // Gets the name of dining hall if a user is physically close to
                    String closeHall = closeHall(location);

                    // Go directly to dining hall if within specified distance
                    if (closeHall != null) {
                        navigateToHallActivity(closeHall);
                    } else {
                        proceedToNextPage();
                    }
                } else {
                    proceedToNextPage();
                }
            });
        } else {
            // Handles the case where permission is not granted
            showLocationPermissionAlert();
        }
    }

}
