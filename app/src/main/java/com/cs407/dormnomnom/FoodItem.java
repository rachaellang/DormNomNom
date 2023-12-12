package com.cs407.dormnomnom;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodItem {
    String name;
    Double calories;
    Double gFat;
    Double gCarbs;
    Double mgSodium;
    Double gProtein;

    int station;

    public FoodItem(String name, Double calories, Double gFat, Double gCarbs, Double mgSodium, Double gProtein) {
        this.name = name;
        this.calories = calories;
        this.gFat = gFat;
        this.gCarbs = gCarbs;
        this.mgSodium = mgSodium;
        this.gProtein = gProtein;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public int getStation() {
        return this.station;
    }

    public String getName(){
        return this.name;
    }

    public double[] getNutrition(){
        double[] facts = new double[5];

        if(this.calories != null) {
            facts[0] = this.calories;
        } else {
            facts[0] = 0.0;
        }

        if(this.gFat != null) {
            facts[1] = this.gFat;
        } else {
            facts[1] = 0.0;
        }

        if(this.gCarbs != null) {
            facts[2] = this.gCarbs;
        } else {
            facts[2] = 0.0;
        }

        if(this.mgSodium != null) {
            facts[3] = this.mgSodium;
        } else {
            facts[3] = 0.0;
        }

        if(this.gProtein != null) {
            facts[4] = this.gProtein;
        } else {
            facts[4] = 0.0;
        }

        return facts;
    }

    // Used to convert object params into strings for activity switching
    public String toJsonString() {
        try {
            JSONObject json = new JSONObject();
            json.put("name", name);
            json.put("calories", calories);
            json.put("gFat", gFat);
            json.put("gCarbs", gCarbs);
            json.put("mgSodium", mgSodium);
            json.put("gProtein", gProtein);
            json.put("station", station);

            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}
