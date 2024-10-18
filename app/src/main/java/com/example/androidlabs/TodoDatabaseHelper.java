package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDatabaseHelper extends SQLiteOpenHelper {
    // Define static variables for DB details
    private static final String DATABASE_NAME = "todoList.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "todos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TODO = "todo";
    public static final String COLUMN_URGENCY = "urgency";

    // Constructor
    public TodoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TODO + " TEXT, " +
                COLUMN_URGENCY + " INTEGER);";
        db.execSQL(createTable);
    }

    // Upgrade the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
