package com.cs407.dormnomnom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final String[] stationNames;
    private final HashMap<String, List<String>> stationFoodMap;

    public CustomExpandableAdapter(Context context, String[] stationNames, HashMap<String, List<String>> stationFoodMap) {
        this.context = context;
        this.stationNames = stationNames;
        this.stationFoodMap = stationFoodMap;
    }

    @Override
    public int getGroupCount() {
        return stationNames != null ? stationNames.length : 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String stationName = stationNames[groupPosition];
        List<String> foodItems = stationFoodMap.get(stationName);
        return foodItems != null ? foodItems.size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return stationNames[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String stationName = stationNames[groupPosition];
        List<String> foodItems = stationFoodMap.get(stationName);
        return foodItems != null ? foodItems.get(childPosition) : null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String stationName = (String) getGroup(groupPosition);

        // Create a LinearLayout for the group view
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create a TextView for the station name
        TextView stationTextView = new TextView(context);
        stationTextView.setText(stationName);
        layout.addView(stationTextView);

        return layout;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String foodItem = (String) getChild(groupPosition, childPosition);

        // Create a LinearLayout for the child view
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create a TextView for the food item
        TextView foodTextView = new TextView(context);
        foodTextView.setText(foodItem);
        layout.addView(foodTextView);

        return layout;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
