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
                + MemberEntry._ID + " INTEGER PRIMARY KEY, "
                + MemberEntry._NAME + "TEXT, "
                + MemberEntry._SURNAME + "TEXT, "
                + MemberEntry._SEX + "INTEGER NOT NULL, "
                + MemberEntry._SPORT_GROUP + "TEXT)";
        sqLiteDatabase.execSQL(CREATE_MEMBERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ClubOlympusContract.DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}
