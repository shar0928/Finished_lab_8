package com.example.androidlabs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView listView;
    private JSONArray characters;
    private boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if we're using the tablet layout (presence of frameLayout)
        isTablet = findViewById(R.id.frameLayout) != null;

        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        fetchData();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            try {
                JSONObject character = characters.getJSONObject(position);
                String name = character.getString("name");
                String height = character.getString("height");
                String mass = character.getString("mass");

                if (isTablet) { // Tablet mode
                    // Use FragmentManager to display DetailsFragment in the frameLayout on tablets
                    DetailsFragment fragment = new DetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", name);
                    bundle.putString("height", height);
                    bundle.putString("mass", mass);
                    fragment.setArguments(bundle);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, fragment)
                            .commit();
                } else { // Phone mode
                    // Start DetailsActivity to show the details on phones
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("height", height);
                    intent.putExtra("mass", mass);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                Log.e(TAG, "Error selecting character", e);
                Toast.makeText(MainActivity.this, "Error selecting character", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchData() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            JSONObject jsonResponse = fetchJsonData();
            if (jsonResponse != null) {
                try {
                    characters = jsonResponse.getJSONArray("results");
                    runOnUiThread(() -> populateListView(characters));
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Parsing error", e);
                }
            } else {
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private JSONObject fetchJsonData() {
        String urlString = "https://swapi.dev/api/people/?format=json";
        try {
            URL apiUrl = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            int data = reader.read();
            while (data != -1) {
                response.append((char) data);
                data = reader.read();
            }
            reader.close();
            return new JSONObject(response.toString());
        } catch (Exception e) {
            Log.e(TAG, "Error fetching data", e);
        }
        return null;
    }

    private void populateListView(JSONArray characters) {
        StarWarsAdapter adapter = new StarWarsAdapter(this, characters);
        listView.setAdapter(adapter);
    }
}
