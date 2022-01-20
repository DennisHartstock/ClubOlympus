package com.example.clubolympus;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;
import com.example.clubolympus.data.MemberCursorAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    ListView dataListView;
    FloatingActionButton floatingActionButton;
    private static final int MEMBER_LOADER = 123;
    MemberCursorAdapter memberCursorAdapter;

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

        memberCursorAdapter = new MemberCursorAdapter(this, null, false);
        dataListView.setAdapter(memberCursorAdapter);

        dataListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
            Uri currentMemberUri = ContentUris.withAppendedId(MemberEntry.CONTENT_URI, l);
            intent.setData(currentMemberUri);
            startActivity(intent);
        });

        LoaderManager.getInstance(this).initLoader(MEMBER_LOADER, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {MemberEntry.COLUMN_ID, MemberEntry.COLUMN_NAME, MemberEntry.COLUMN_SURNAME,
                MemberEntry.COLUMN_SEX, MemberEntry.COLUMN_SPORTS_GROUP};

        return new CursorLoader(this, MemberEntry.CONTENT_URI, projection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        memberCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        memberCursorAdapter.swapCursor(null);
    }
}