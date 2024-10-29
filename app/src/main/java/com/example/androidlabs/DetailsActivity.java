package com.example.androidlabs;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get the Intent extras passed from MainActivity
        String name = getIntent().getStringExtra("name");
        String height = getIntent().getStringExtra("height");
        String mass = getIntent().getStringExtra("mass");

        // Set the data to TextViews
        TextView nameTextView = findViewById(R.id.textViewName);
        TextView heightTextView = findViewById(R.id.textViewHeight);
        TextView massTextView = findViewById(R.id.textViewMass);

        nameTextView.setText("Name: " + name);
        heightTextView.setText("Height: " + height);
        massTextView.setText("Mass: " + mass);
    }
}
