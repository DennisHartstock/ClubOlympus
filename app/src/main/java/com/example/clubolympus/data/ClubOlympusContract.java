package com.example.clubolympus.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ClubOlympusContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "clubOlympusDB";

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.clubolympus";
    public static final String PATH_MEMBERS = "members";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    private ClubOlympusContract() {

    }

    public static final class MemberEntry implements BaseColumns {
        public static final String TABLE_NAME = "members";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS);

        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SURNAME = "surname";
        public static final String COLUMN_SEX = "sex";
        public static final String COLUMN_SPORTS_GROUP = "sportsGroup";
        public static final int SEX_FEMALE_CODE = 0;
        public static final int SEX_MALE_CODE = 1;

        public static final String CONTENT_MULTIPLE_ITEMS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                + AUTHORITY + "/" + PATH_MEMBERS;
        public static final String CONTENT_SINGLE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/"
                + AUTHORITY + "/" + PATH_MEMBERS;
    }

}