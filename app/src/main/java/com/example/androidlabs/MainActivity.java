package com.example.androidlabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ImageView catImageView;
    private ProgressBar progressBar;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        catImageView = findViewById(R.id.catImageView);
        progressBar = findViewById(R.id.progressBar);

        // Using ExecutorService instead of AsyncTask
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new CatImages(this));
    }

    private static class CatImages implements Runnable {
        private final WeakReference<MainActivity> activityReference;
        private Bitmap bitmap;

        // Constructor to pass a reference to MainActivity
        public CatImages(MainActivity activity) {
            activityReference = new WeakReference<>(activity);
        }

        @Override
        public void run() {
            MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) return;

            try {
                // Get the cat image JSON URL
                JSONObject jsonObject = fetchCatImageJson();
                String imageUrl = "https://cataas.com" + jsonObject.getString("url");

                // Check if the image exists locally
                String fileName = jsonObject.getString("id") + ".png";
                File file = new File(activity.getApplicationContext().getFilesDir(), fileName);

                if (file.exists()) {
                    bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                } else {
                    // Download the image
                    URL imageURL = new URL(imageUrl);
                    HttpURLConnection imageConnection = (HttpURLConnection) imageURL.openConnection();
                    bitmap = BitmapFactory.decodeStream(imageConnection.getInputStream());

                    // Save the image locally
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    outputStream.close();
                }

                // Simulate progress
                for (int i = 0; i < 100; i++) {
                    int finalI = i;
                    new Handler(Looper.getMainLooper()).post(() -> activity.progressBar.setProgress(finalI));
                    Thread.sleep(30);
                }

                // Update UI with the downloaded image
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (bitmap != null) {
                        activity.catImageView.setImageBitmap(bitmap);
                    }
                });

            } catch (Exception e) {
                Log.e(TAG, "Error fetching image: ", e);
            }
        }

        private JSONObject fetchCatImageJson() throws Exception {
            URL url = new URL("https://cataas.com/cat?json=true");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }

            return new JSONObject(jsonBuilder.toString());
        }
    }
}
