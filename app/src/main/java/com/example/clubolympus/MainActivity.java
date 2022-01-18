package com.example.clubolympus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;
import com.example.clubolympus.data.MemberCursorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    ListView dataListView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataListView = findViewById(R.id.dataListView);

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

        MemberCursorAdapter memberCursorAdapter = new MemberCursorAdapter(this, cursor, false);
        dataListView.setAdapter(memberCursorAdapter);
    }
}