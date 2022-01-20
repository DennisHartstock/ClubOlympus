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
        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS_CODE:
                return MemberEntry.CONTENT_MULTIPLE_ITEMS;

            case MEMBER_ID_CODE:
                return MemberEntry.CONTENT_SINGLE_ITEM;

            default:
                Toast.makeText(getContext(), "Unknown Uri", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Unknown Uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        //checkInput(uri, contentValues);

        SQLiteDatabase database = clubOlympusDbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        if (match == MEMBERS_CODE) {
            long id = database.insert(MemberEntry.TABLE_NAME, null, contentValues);

            if (id == -1) {
                Log.e("insertMethod", "Insertion of data in the table failed for " + uri);
                return null;
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return ContentUris.withAppendedId(uri, id);
        }
        throw new IllegalArgumentException("Insertion of data in the table failed for " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase database = clubOlympusDbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case MEMBERS_CODE:
                rowsDeleted = database.delete(MemberEntry.TABLE_NAME, s, strings);
                break;

            case MEMBER_ID_CODE:
                s = MemberEntry.COLUMN_ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(MemberEntry.TABLE_NAME, s, strings);
                break;

            default:
                Toast.makeText(getContext(), "Can't delete this Uri", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't delete this Uri " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase database = clubOlympusDbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MEMBERS_CODE:
                rowsUpdated = database.update(MemberEntry.TABLE_NAME, contentValues, s, strings);
                break;

            case MEMBER_ID_CODE:
                s = MemberEntry.COLUMN_ID + "=?";
                strings = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated = database.update(MemberEntry.TABLE_NAME, contentValues, s, strings);
                break;

            default:
                Toast.makeText(getContext(), "Can't update this Uri", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't update this Uri " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}