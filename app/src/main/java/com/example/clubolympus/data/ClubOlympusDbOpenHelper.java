package com.example.clubolympus.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;

public class ClubOlympusDbOpenHelper extends SQLiteOpenHelper {

    public ClubOlympusDbOpenHelper(@Nullable Context context) {
        super(context, ClubOlympusContract.DATABASE_NAME, null, ClubOlympusContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_MEMBERS_TABLE = "CREATE TABLE " + MemberEntry.TABLE_NAME + "("
                + MemberEntry.COLUMN_ID + " INTEGER PRIMARY KEY,"
                + MemberEntry.COLUMN_NAME + " TEXT,"
                + MemberEntry.COLUMN_SURNAME + " TEXT,"
                + MemberEntry.COLUMN_SEX + " INTEGER NOT NULL,"
                + MemberEntry.COLUMN_SPORTS_GROUP + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_MEMBERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ClubOlympusContract.DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}