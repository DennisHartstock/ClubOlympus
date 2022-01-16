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

        int idIndex = cursor.getColumnIndex(MemberEntry.COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(MemberEntry.COLUMN_NAME);
        int surnameIndex = cursor.getColumnIndex(MemberEntry.COLUMN_SURNAME);
        int sexIndex = cursor.getColumnIndex(MemberEntry.COLUMN_SEX);
        int sportsGroupIndex = cursor.getColumnIndex(MemberEntry.COLUMN_SPORTS_GROUP);

        while (cursor.moveToNext()) {
            int currentId = cursor.getInt(idIndex);
            String currentName = cursor.getString(nameIndex);
            String currentSurname = cursor.getString(surnameIndex);
            int currentSex = cursor.getInt(sexIndex);
            String currentSportsGroup = cursor.getString(sportsGroupIndex);

            dataTextView.append("\n" + currentId + " " + currentName + " " + currentSurname + " "
                    + currentSex + " " + currentSportsGroup);
        }
        cursor.close();
    }
}