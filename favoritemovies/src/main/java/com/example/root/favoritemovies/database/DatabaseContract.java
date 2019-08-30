package com.example.root.favoritemovies.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.example.root.moviestwo";
    private static final String SCHEME = "content";

    public static final class FavoriteColumns implements BaseColumns {

        public static final String TABLE_MOVIE = "favorite";
        public static final String TITLE_MOVIE = "title";
        public static final String POSTER_MOVIE = "poster";
        public static final String BACKGROUND_MOVIE = "background";
        public static final String GENRE_MOVIE = "genre";
        public static final String DESC_MOVIE = "description";
        public static final String RELEASE_MOVIE = "release";
        public static final String LANG_MOVIE = "language";
        public static final String POPULAR_MOVIE = "popular";
        public static final String RATING_MOVIE = "rating";
        public static final String CATEGORY_MOVIE = "category";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();

        public static String getColumnString(Cursor cursor, String columnName) {
            return cursor.getString(cursor.getColumnIndex(columnName));
        }

        public static int getColumnInt(Cursor cursor, String columnName) {
            return cursor.getInt(cursor.getColumnIndex(columnName));
        }

    }
}
