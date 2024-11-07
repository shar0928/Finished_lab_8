package com.example.androidlabs;

import android.os.Bundle;
import android.widget.TextView;

public class DadJoke extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dad_joke);

        // Set up Toolbar and Navigation Drawer
        setupToolbarAndDrawer();

        // Set joke text
        TextView jokeTextView = findViewById(R.id.joke_text_view);
        jokeTextView.setText("Why don't scientists trust atoms? Because they make up everything!");


    }
}
