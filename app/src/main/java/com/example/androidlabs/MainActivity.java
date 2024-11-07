package com.example.androidlabs;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the Navigation Drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the toolbar_menu.xml
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle menu item clicks using if-else statements
        int itemId = item.getItemId();
        if (itemId == R.id.item1) {
            Toast.makeText(this, "You clicked on item 1", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.item2) {
            Toast.makeText(this, "You clicked on item 2", Toast.LENGTH_SHORT).show();
            return true;
        } else if (itemId == R.id.overflow_item) {
            // Handle overflow item click
            Toast.makeText(this, "Overflow item clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation item clicks using if-else statements
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            // If Home is selected, just show the home content
            Toast.makeText(this, "Home selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.nav_dad_joke) {
            // Launch Dad Joke activity when selected
            Intent intent = new Intent(this, DadJoke.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_exit) {
            // Exit the app when Exit is selected
            finishAffinity();
            return true;
        } else {
            return false;
        }
    }
}
