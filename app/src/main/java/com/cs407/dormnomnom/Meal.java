package com.cs407.dormnomnom;

import java.util.ArrayList;

public class Meal {
    ArrayList<FoodItem> menuItems;
    String name;

    public Meal(String name) {
        this.name = name;
        this.menuItems = new ArrayList<FoodItem>();
    }

    public String getName() {
        return this.name;
    }

    public void addItem(FoodItem item) {
        this.menuItems.add(item);
    }

    public ArrayList<FoodItem> getMenuItems() {
        return this.menuItems;
    }
}