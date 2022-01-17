package com.example.clubolympus;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;

public class MainActivity extends AppCompatActivity {
    TextView dataTextView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTextView = findViewById(R.id.dataTextView);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    @SuppressLint("Recycle")
    private void displayData() {
        String[] projection = {MemberEntry.COLUMN_ID, MemberEntry.COLUMN_NAME, MemberEntry.COLUMN_SURNAME,
                MemberEntry.COLUMN_SEX, MemberEntry.COLUMN_SPORTS_GROUP};

        Cursor cursor = getContentResolver().query(MemberEntry.CONTENT_URI, projection,
                null, null, null);

        dataTextView.setText("All members\n\n");
        dataTextView.append(MemberEntry.COLUMN_ID + " " + MemberEntry.COLUMN_NAME + " " + MemberEntry.COLUMN_SURNAME + " "
                + MemberEntry.COLUMN_SEX + " " + MemberEntry.COLUMN_SPORTS_GROUP);

        int idColumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_ID);
        int nameColumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_NAME);
        int surnameColumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_SURNAME);
        int sexColumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_SEX);
        int sportsGroupColumnIndex = cursor.getColumnIndex(MemberEntry.COLUMN_SPORTS_GROUP);

        while (cursor.moveToNext()) {
            int currentId = cursor.getInt(idColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            String currentSurname = cursor.getString(surnameColumnIndex);
            int currentSex = cursor.getInt(sexColumnIndex);
            String currentSportsGroup = cursor.getString(sportsGroupColumnIndex);

            dataTextView.append("\n" + currentId + " " + currentName + " " + currentSurname + " "
                    + currentSex + " " + currentSportsGroup);
        }
        cursor.close();
    }
}