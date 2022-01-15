package com.example.clubolympus.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class ClubOlympusContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME ="clubOlympusDb";

    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.clubolympus";
    public static final String PATH_MEMBERS = "members";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);

    private ClubOlympusContract() {

    }

    public static final class MemberEntry implements BaseColumns {
        public static final String TABLE_NAME = "members";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS);

        public static final String _ID = BaseColumns._ID;
        public static final String _NAME = "name";
        public static final String _SURNAME = "surname";
        public static final String _SEX = "sex";
        public static final String _SPORTS_GROUP = "sportsGroup";
        public static final int _SEX_FEMALE = 0;
        public static final int _SEX_MALE = 1;

    }


}
