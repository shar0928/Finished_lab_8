package com.example.androidlabs;

import android.os.Bundle;
import android.view.MenuItem;  // Import MenuItem class
import androidx.annotation.NonNull;  // Import NonNull annotation
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Set the content view here
    }

    protected void setupToolbarAndDrawer() {
        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the Navigation Drawer
        findViewById(R.id.drawer_layout);// Use drawerLayout instead of drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation item clicks
        if (item.getItemId() == R.id.nav_home) {
            // Navigate to MainActivity
            finish();
            return true;
        } else if (item.getItemId() == R.id.nav_dad_joke) {
            // Stay on DadJoke activity
            return true;
        } else if (item.getItemId() == R.id.nav_exit) {
            finishAffinity();  // Close all activities
            return true;
        }
        return false;
    }
}
