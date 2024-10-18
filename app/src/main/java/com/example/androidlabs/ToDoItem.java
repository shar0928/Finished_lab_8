package com.example.androidlabs;

public class ToDoItem {
    private final String text;      // The text of the To-Do item
    private final boolean isUrgent; // Indicates if the item is urgent

    // Constructor
    public ToDoItem(String text, boolean isUrgent) {
        this.text = text;
        this.isUrgent = isUrgent;
    }

    // Getter for text
    public String getText() {
        return text;
    }

    // Getter for urgency
    public boolean isUrgent() {
        return isUrgent;
    }
}
