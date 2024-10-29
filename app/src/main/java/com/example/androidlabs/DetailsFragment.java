package com.example.androidlabs; // Replace with your actual package name

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Get the data from arguments
        assert getArguments() != null;
        String name = getArguments().getString("name");
        String height = getArguments().getString("height");
        String mass = getArguments().getString("mass");

        // Find TextViews
        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewHeight = view.findViewById(R.id.textViewHeight);
        TextView textViewMass = view.findViewById(R.id.textViewMass);

        // Set the text in the desired format
        textViewName.setText("Name: " + (name != null ? name : "No name available"));
        textViewHeight.setText("Height: " + (height != null ? height : "No height available"));
        textViewMass.setText("Mass: " + (mass != null ? mass : "No mass available"));

        return view;
    }
}
