package com.example.androidlabs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        // Create an instance of DetailsFragment
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(getIntent().getExtras());

        // Use Fragment Manager to load the DetailsFragment into the container
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, detailsFragment)
                .commit();
    }
}
