package com.example.androidlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;

public class StarWarsAdapter extends BaseAdapter {
    private final JSONArray characters; // Marked as final
    private final Context context; // Marked as final
    private static final String TAG = "StarWarsAdapter"; // Tag for logging

    public StarWarsAdapter(Context context, JSONArray characters) {
        this.context = context;
        this.characters = characters;
    }

    @Override
    public int getCount() {
        return characters.length();
    }

    @Override
    public Object getItem(int position) {
        return characters.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = (TextView) convertView;
        try {
            JSONObject character = characters.getJSONObject(position);
            textView.setText(character.getString("name")); // Display character name
        } catch (Exception e) {
            Log.e(TAG, "Error getting character name at position " + position, e); // Better logging
        }
        return convertView;
    }
}
