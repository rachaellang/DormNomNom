package com.cs407.dormnomnom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class StationActivity extends AppCompatActivity {

    //Kamila Domagala
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        String station = getIntent().getStringExtra("STATION");
        String year = getIntent().getStringExtra("YEAR");
        String month = getIntent().getStringExtra("MONTH");
        String day = getIntent().getStringExtra("DAY");
        String meal = getIntent().getStringExtra("MEAL");
        String hallName = getIntent().getStringExtra("HALLNAME");

        TextView diningHallView = findViewById(R.id.foodName);
        diningHallView.setText(station);

        new AsyncTask<Void, Void, ArrayList<FoodItem>>() {
            @Override
            protected ArrayList<FoodItem> doInBackground(Void... voids) {
                try {
                    GetRequest request = new GetRequest(year, month, day, meal, hallName);
                    return request.getMenuItems();
                } catch (Exception e) {
                    Log.e("MenuFetchError", "Error fetching menu", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ArrayList<FoodItem> menu) {
                String[] nameArr = new String[menu.size()];
                for (int i = 0; i < menu.size(); i++) {
                    nameArr[i] = menu.get(i).name;
                }
                updateListView(nameArr, year, month, day, meal, hallName);

            }
        }.execute();
    }

    private void updateListView(String[] nameArr, String year, String month, String day, String meal, String hallName) {
        ListView menuList = findViewById(R.id.menuList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nameArr);
        menuList.setAdapter(adapter);

        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(StationActivity.this, FoodActivity.class);
                i.putExtra("STATION", nameArr[position]);
                i.putExtra("YEAR", year);
                i.putExtra("MONTH", month);
                i.putExtra("DAY", day);
                i.putExtra("MEAL", meal);
                i.putExtra("HALLNAME", hallName);

                startActivity(i);
                finish();            }
        });
    }
}