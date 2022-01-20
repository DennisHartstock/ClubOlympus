package com.example.clubolympus;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;

public class AddMemberActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int EDIT_MEMBER_LOADER = 111;
    private Uri currentMemberUri;
    private EditText nameEditText;
    private EditText surnameEditText;
    private Spinner sexSpinner;
    private EditText sportsGroupEditText;
    private int sex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Intent intent = getIntent();
        currentMemberUri = intent.getData();

        if (currentMemberUri == null) {
            setTitle("Add a new member");
        } else {
            setTitle("Edit the member");
            LoaderManager.getInstance(this).initLoader(EDIT_MEMBER_LOADER, null, this);
        }

        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        sexSpinner = findViewById(R.id.sexSpinner);
        sportsGroupEditText = findViewById(R.id.sportsGroupEditText);

        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_sex, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(spinnerAdapter);

        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSex = (String) adapterView.getItemAtPosition(i);

                if (selectedSex.equals("Male")) {
                    sex = MemberEntry.SEX_MALE_CODE;
                } else {
                    sex = MemberEntry.SEX_FEMALE_CODE;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                sex = MemberEntry.SEX_FEMALE_CODE;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member_menu, menu);

        if (currentMemberUri == null) {
            MenuItem menuItem = menu.findItem(R.id.delete_member);
            menuItem.setVisible(false);
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_member:
                saveMember();
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.delete_member:
                showDeleteMemberDialog();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteMemberDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Delete this member?");
        alertDialogBuilder.setPositiveButton("Delete", (dialogInterface, i) -> deleteMember());
        alertDialogBuilder.setNegativeButton("Cancel", (dialogInterface, i) -> {

            if (dialogInterface != null) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void deleteMember() {

        if (currentMemberUri != null) {
            int rowsDeleted = getContentResolver().delete(currentMemberUri, null, null);
            
            if (rowsDeleted == 0) {
                Toast.makeText(this, "Member deletion failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Member deleted", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }

    private void saveMember() {
        String name = nameEditText.getText().toString().trim();
        String surname = surnameEditText.getText().toString().trim();
        String sportsGroup = sportsGroupEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(surname) || TextUtils.isEmpty(sportsGroup)) {
            Toast.makeText(this, "Input data", Toast.LENGTH_LONG).show();
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(MemberEntry.COLUMN_NAME, name);
        contentValues.put(MemberEntry.COLUMN_SURNAME, surname);
        contentValues.put(MemberEntry.COLUMN_SPORTS_GROUP, sportsGroup);
        contentValues.put(MemberEntry.COLUMN_SEX, sex);

        if (currentMemberUri == null) {
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(MemberEntry.CONTENT_URI, contentValues);

            if (uri == null) {
                Toast.makeText(this, "Insertion of data in the table failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
            }
        } else {
            int rowsChanged = getContentResolver().update(currentMemberUri, contentValues, null, null);

            if (rowsChanged != 0) {
                Toast.makeText(this, "Data updated", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Make changes or return", Toast.LENGTH_LONG).show();
            }
        }

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {MemberEntry.COLUMN_ID, MemberEntry.COLUMN_NAME, MemberEntry.COLUMN_SURNAME,
                MemberEntry.COLUMN_SEX, MemberEntry.COLUMN_SPORTS_GROUP};

        return new CursorLoader(this, currentMemberUri, projection,
                null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        if (data.moveToFirst()) {
            int nameColumnIndex = data.getColumnIndex(MemberEntry.COLUMN_NAME);
            int surnameColumnIndex = data.getColumnIndex(MemberEntry.COLUMN_SURNAME);
            int sexColumnIndex = data.getColumnIndex(MemberEntry.COLUMN_SEX);
            int sportsGroupColumnIndex = data.getColumnIndex(MemberEntry.COLUMN_SPORTS_GROUP);

            String name = data.getString(nameColumnIndex);
            String surname = data.getString(surnameColumnIndex);
            int sex = data.getInt(sexColumnIndex);
            String sportsGroup = data.getString(sportsGroupColumnIndex);

            nameEditText.setText(name);
            surnameEditText.setText(surname);
            sexSpinner.setSelection(sex);
            sportsGroupEditText.setText(sportsGroup);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}