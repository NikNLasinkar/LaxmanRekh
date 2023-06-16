package com.example.laxrek;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ChildActivity extends AppCompatActivity {
    Spinner _selectParent;
    SQLiteDatabase db;
    SQLiteOpenHelper openHelper;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);
        openHelper=new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        _selectParent = (Spinner)findViewById(R.id.spinner);

        String[] projection = { "FirstName", "LastName" };

// Define the selection criteria
        String selection = "UserType = ?";
        String[] selectionArgs = { "Parent" };

// Execute the query
        Cursor cursor = db.query(
                "registeration",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );


// Create a list to store the retrieved data
        List<String> names = new ArrayList<>();

// Iterate through the cursor to retrieve the data
        while (cursor.moveToNext()) {
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow("FirstName"));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow("LastName"));

            // Concatenate first name and last name
            String fullName = firstName + " " + lastName;

            // Add the full name to the list
            names.add(fullName);
        }

// Close the cursor and the database
        cursor.close();
        db.close();




// Create an ArrayAdapter to populate the dropdown list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Get reference to the dropdown list view
        @SuppressLint("CutPasteId") Spinner dropdown = findViewById(R.id.spinner); // Replace with actual dropdown ID

// Set the adapter to the dropdown list
        dropdown.setAdapter(adapter);
    }
}