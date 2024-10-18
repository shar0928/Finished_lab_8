package com.example.androidlabs;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat; // Use ContextCompat for getColor()
import androidx.appcompat.widget.SwitchCompat; // Use SwitchCompat from AppCompat

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ToDoItem> toDoList;  // List to hold To-Do items
    private BaseAdapter adapter;      // Adapter to manage the ListView
    private EditText inputText;       // EditText for user input
    private SwitchCompat urgentSwitch; // SwitchCompat from AppCompat for compatibility

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up UI elements
        inputText = findViewById(R.id.editText);
        urgentSwitch = findViewById(R.id.urgentSwitch);
        ListView listView = findViewById(R.id.listView);

        // Initialize the list to hold To-Do items
        toDoList = new ArrayList<>();

        // Set up the adapter to manage the ListView
        adapter = new ToDoAdapter();
        listView.setAdapter(adapter);

        // Handle the "Add" button click
        Button addButton = findViewById(R.id.addButton);
        addButton.setText(R.string.add_button); // Set button text from resources
        addButton.setOnClickListener(v -> {
            String text = inputText.getText().toString(); // Get text from EditText
            boolean isUrgent = urgentSwitch.isChecked();  // Check if urgent switch is on

            // Only add the item if text is not empty
            if (!text.isEmpty()) {
                // Create a new ToDoItem and add it to the list
                toDoList.add(new ToDoItem(text, isUrgent));

                // Notify the adapter to refresh the ListView
                adapter.notifyDataSetChanged();

                // Clear the EditText for new input
                inputText.setText("");
            }
        });

        // Set long click listener to delete an item
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.delete_title)
                    .setMessage(getString(R.string.delete_message, position))
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        // Delete the item at the selected position
                        toDoList.remove(position);
                        adapter.notifyDataSetChanged(); // Refresh the list
                    })
                    .setNegativeButton(R.string.no, null) // No action on "No"
                    .show(); // Show the AlertDialog
            return true; // Indicates the long click was handled
        });
    }

    // Inner class for the Adapter
    private class ToDoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return toDoList.size(); // Return the number of items in the list
        }

        @Override
        public Object getItem(int position) {
            return toDoList.get(position); // Return the ToDo item at this position
        }

        @Override
        public long getItemId(int position) {
            return position; // Return the position as the ID
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Use the ViewHolder pattern to improve performance
            ViewHolder holder;

            // Check if the existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.todo_item, parent, false);
                holder = new ViewHolder();
                holder.textView = convertView.findViewById(R.id.textView);
                convertView.setTag(holder); // Store the view holder with the view
            } else {
                holder = (ViewHolder) convertView.getTag(); // Retrieve the view holder
            }

            // Get the ToDoItem for the current position
            ToDoItem item = toDoList.get(position);
            holder.textView.setText(item.getText()); // Set the text for the TextView

            // Change the background and text color based on urgency using ContextCompat
            if (item.isUrgent()) {
                convertView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_dark));
                holder.textView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
            } else {
                convertView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                holder.textView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
            }

            return convertView; // Return the completed view to render on screen
        }

        // ViewHolder pattern to hold view references
        private class ViewHolder {
            TextView textView; // Reference to the TextView for the ToDo item
        }
    }
}
