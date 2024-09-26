package com.example.androidlabs;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear); // Use the appropriate layout (linear/grid/constraint)

        // Referencing UI elements
        TextView textView = findViewById(R.id.text_view);
        EditText editText = findViewById(R.id.edit_text);
        Button pressMeButton = findViewById(R.id.press_me_button);
        CheckBox checkBox = findViewById(R.id.checkbox);
        Switch switchToggle = findViewById(R.id.switch_toggle);
        ImageButton flagButton = findViewById(R.id.flag_button);

        // Button onClickListener: Updates TextView and shows a Toast
        pressMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = editText.getText().toString();
                textView.setText(inputText);  // Update TextView with EditText content

                // Show a Toast message
                Toast.makeText(MainActivity.this, getString(R.string.toast_message), Toast.LENGTH_SHORT).show();
            }
        });

        // CheckBox onCheckedChangeListener: Shows a Snackbar with Undo option
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String message = "The checkbox is now " + (isChecked ? "on" : "off");
            Snackbar snackbar = Snackbar.make(buttonView, message, Snackbar.LENGTH_LONG);

            // Add Undo action to Snackbar
            snackbar.setAction("Undo", v -> checkBox.setChecked(!isChecked));
            snackbar.show();
        });
    }
}