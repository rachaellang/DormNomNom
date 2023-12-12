package com.cs407.dormnomnom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableAdapter extends BaseExpandableListAdapter {

    private final Context context;
    private final String[] stationNames;
    private final HashMap<String, List<FoodItem>> stationFoodMap;

    public CustomExpandableAdapter(Context context, String[] stationNames, HashMap<String, List<FoodItem>> stationFoodMap) {
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
        List<FoodItem> foodItems = stationFoodMap.get(stationName);
        return foodItems != null ? foodItems.size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return stationNames[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String stationName = stationNames[groupPosition];
        List<FoodItem> foodItems = stationFoodMap.get(stationName);
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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_item, null);
        }

        TextView groupItemTextView = convertView.findViewById(R.id.groupItemTextView);
        groupItemTextView.setText(stationName);

        // Set indicator bounds to move it to the right side
        ExpandableListView expandableListView = (ExpandableListView) parent;
        expandableListView.setIndicatorBoundsRelative(
                expandableListView.getWidth() - 100, expandableListView.getWidth());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        FoodItem foodItem = (FoodItem) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView childItemTextView = convertView.findViewById(R.id.childItemTextView);
        childItemTextView.setText(foodItem != null ? foodItem.getName() : "");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
