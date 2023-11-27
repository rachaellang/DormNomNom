package com.cs407.dormnomnom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class GetRequest {
    //Date must follow format year/month/day exactly
    String year;
    String month;
    String day;
    //Either breakfast, lunch, or dinner
    String meal;

    /**
     * Valid locations:
     * four-lakes-market
     * carsons-market
     * lizs-market
     * gordon-avenue-market
     * rhetas-market
     * lowell-market
     */
    String location;
    ArrayList<FoodItem> menuItems;
    ArrayList<Station> stations;

    public GetRequest(String year, String month, String day, String meal, String location) throws JSONException {
        this.year = year;
        this.month = month;
        this.day = day;
        this.meal = meal;
        this.location = location;

        String response = makeRequest();

        if (response != null) {
            menuItems = parseMenu(response);
            assignStations();
        } else {
            throw new RuntimeException("Network Error");
        }
    }

    private String makeRequest() {
        String date = this.year + "/" + this.month + "/" + this.day;

        String requestURI = "https://wisc-housingdining.api.nutrislice.com/menu/api/weeks/school/"
                + this.location + "/menu-type/" + this.meal + "/" + date + "/";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(requestURI)
                .build();

        try {
            Response response = client.newCall(request).execute();
            // Check if response is successful and has a body
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string(); // Returns the response body as a string
            } else {
                throw new IOException("Unexpected code " + response);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private ArrayList<FoodItem> parseMenu(String response) throws JSONException {
        String date = this.year + "-" + this.month + "-" + this.day;

        JSONObject JSON = new JSONObject(response);
        JSONArray daysArray = JSON.getJSONArray("days");

        JSONObject day = getCorrectDay(date, daysArray);
        JSONObject menu_info = day.getJSONObject("menu_info");
        JSONArray menuItems = day.getJSONArray("menu_items");

        this.stations = parseStations(menu_info);
        ArrayList<FoodItem> foodItems= parseMenuItems(menuItems);

        return foodItems;
    }

    private JSONObject getCorrectDay(String date, JSONArray days) throws JSONException {
        for (int i = 0; i < days.length(); i++) {
            if (days.getJSONObject(i).get("date").equals(date)) {
                return days.getJSONObject(i);
            }
        }

        throw new RuntimeException("Date Error");
    }

    private ArrayList<FoodItem> parseMenuItems (JSONArray menuItems) throws JSONException {
        ArrayList<FoodItem> foundFood = new ArrayList<>();

        for (int i = 0; i < menuItems.length(); i++) {
            if (menuItems.getJSONObject(i).isNull("food")) {
                continue;
            } else {
                JSONObject foodItem = menuItems.getJSONObject(i).getJSONObject("food");
                JSONObject nutrition = foodItem.getJSONObject("rounded_nutrition_info");

                double calories = nutrition.optDouble("calories", 0.0); // Default value as 0.0
                double gFat = nutrition.optDouble("g_fat", 0.0);
                double gCarbs = nutrition.optDouble("g_carbs", 0.0);
                double mgSodium = nutrition.optDouble("mg_sodium", 0.0);
                double gProtein = nutrition.optDouble("g_protein", 0.0);

                FoodItem item = new FoodItem(foodItem.getString("name"), calories, gFat, gCarbs, mgSodium, gProtein);

                item.setStation(menuItems.getJSONObject(i).getInt("menu_id"));

                foundFood.add(item);
            }
        }
        return foundFood;
    }

    private ArrayList<Station> parseStations(JSONObject menu_info) throws JSONException {
        Iterator<String> keys = menu_info.keys();

        ArrayList<Station> stationsList = new ArrayList<Station>();

        while (keys.hasNext()) {
            String key = keys.next();
            if (menu_info.get(key) instanceof JSONObject) {
                JSONObject tempStation = menu_info.getJSONObject(key);
                JSONObject section_options = tempStation.getJSONObject("section_options");
                String name = section_options.getString("display_name");
                int id_temp = Integer.parseInt(key);

                Station testStation = new Station(name, id_temp);
                stationsList.add(testStation);
            }

        }

        return stationsList;
    }

    private void assignStations() {
        for (FoodItem item : this.menuItems) {
            int id = item.getStation();

            for (Station station : this.stations) {
                if (station.getId() == id) {
                    station.addItem(item);
                }
            }
        }
    }

    public ArrayList<FoodItem> getMenuItems() {
        return this.menuItems;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }


}

