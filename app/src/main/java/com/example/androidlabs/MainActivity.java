package com.example.androidlabs;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.SwitchCompat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ToDoItem> toDoList;
    private BaseAdapter adapter;
    private EditText inputText;
    private SwitchCompat urgentSwitch;
    private TodoDatabaseHelper dbHelper;  // SQLite Database Helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the database helper
        dbHelper = new TodoDatabaseHelper(this);

        // Set up UI elements
        inputText = findViewById(R.id.editText);
        urgentSwitch = findViewById(R.id.urgentSwitch);
        ListView listView = findViewById(R.id.listView);

        // Initialize the list and load from the database
        toDoList = new ArrayList<>();
        loadTodosFromDatabase();  // Load saved todos

        // Set up the adapter
        adapter = new ToDoAdapter();
        listView.setAdapter(adapter);

        // Handle the "Add" button click
        Button addButton = findViewById(R.id.addButton);
        addButton.setText(R.string.add_button);
        addButton.setOnClickListener(v -> {
            String text = inputText.getText().toString();
            boolean isUrgent = urgentSwitch.isChecked();

            if (!text.isEmpty()) {
                // Add the new todo to the database
                addTodoToDatabase(text, isUrgent);

                // Reload todos from the database after adding
                loadTodosFromDatabase();
                adapter.notifyDataSetChanged();

                // Clear the EditText
                inputText.setText("");
            }
        });

        // Set long click listener to delete an item
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.delete_title)
                    .setMessage(getString(R.string.delete_message, position))
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        // Remove from the database
                        deleteTodoFromDatabase(position);

                        // Reload todos from the database after deletion
                        loadTodosFromDatabase();
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();
            return true;
        });
    }

    // Load todos from the SQLite database
    private void loadTodosFromDatabase() {
        toDoList.clear();  // Clear the existing list
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TodoDatabaseHelper.TABLE_NAME, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String todoText = cursor.getString(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_TODO));
            int urgency = cursor.getInt(cursor.getColumnIndexOrThrow(TodoDatabaseHelper.COLUMN_URGENCY));
            boolean isUrgent = urgency == 1;
            toDoList.add(new ToDoItem(todoText, isUrgent));
        }

        cursor.close();
        db.close();
    }

    // Add a new todo item to the database
    private void addTodoToDatabase(String todoText, boolean isUrgent) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoDatabaseHelper.COLUMN_TODO, todoText);
        values.put(TodoDatabaseHelper.COLUMN_URGENCY, isUrgent ? 1 : 0);

        db.insert(TodoDatabaseHelper.TABLE_NAME, null, values);
        db.close();
    }

    // Delete a todo item from the database
    private void deleteTodoFromDatabase(int position) {
        ToDoItem item = toDoList.get(position);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TodoDatabaseHelper.TABLE_NAME, TodoDatabaseHelper.COLUMN_TODO + " = ?", new String[]{item.getText()});
        db.close();
    }

    // Adapter for displaying the list of ToDo items
    private class ToDoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return toDoList.size();
        }

        @Override
        public Object getItem(int position) {
            return toDoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.todo_item, parent, false);
                holder = new ViewHolder();
                holder.textView = convertView.findViewById(R.id.textView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ToDoItem item = toDoList.get(position);
            holder.textView.setText(item.getText());

            if (item.isUrgent()) {
                convertView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.holo_red_dark));
                holder.textView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
            } else {
                convertView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, android.R.color.white));
                holder.textView.setTextColor(ContextCompat.getColor(MainActivity.this, android.R.color.black));
            }

            return convertView;
        }

        private class ViewHolder {
            TextView textView;
        }
    }
}
