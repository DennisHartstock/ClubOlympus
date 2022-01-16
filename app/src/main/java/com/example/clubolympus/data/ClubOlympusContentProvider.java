package com.example.clubolympus.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.clubolympus.data.ClubOlympusContract.*;

public class ClubOlympusContentProvider extends ContentProvider {
    ClubOlympusDbOpenHelper clubOlympusDbOpenHelper;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final int MEMBERS_CODE = 111;
    public static final int MEMBER_ID_CODE = 222;

    static {
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS, MEMBERS_CODE);
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS + "/#", MEMBER_ID_CODE);
    }

    @Override
    public boolean onCreate() {
        clubOlympusDbOpenHelper = new ClubOlympusDbOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase database = clubOlympusDbOpenHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS_CODE:
                cursor = database.query(MemberEntry.TABLE_NAME, strings, s, strings1, null, null, s1);
                break;

            case MEMBER_ID_CODE:
                s = MemberEntry.COLUMN_ID + "=?";
                strings1 = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(MemberEntry.TABLE_NAME, strings, s, strings1, null, null, s1);
                break;

            default:
                Toast.makeText(getContext(), "Incorrect Uri", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't query incorrect Uri " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase database = clubOlympusDbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        if (match == MEMBERS_CODE) {
            long id = database.insert(MemberEntry.TABLE_NAME, null, contentValues);

            if (id == -1) {
                Log.e("insertMethod", "Insertion of data in the table failed for " + uri);
                return null;
            }

            return ContentUris.withAppendedId(uri, id);
        }
        throw new IllegalArgumentException("Insertion of data in the table failed for " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}