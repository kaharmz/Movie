package com.example.root.moviestwo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.TABLE_MOVIE;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;
    private static final String SQL_CREATE_TABLE_FAVORITE_MOVIE =
            String.format("CREATE TABLE %s"
                            + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL)",
                    TABLE_MOVIE,
                    DatabaseContract.FavoriteColumns._ID,
                    DatabaseContract.FavoriteColumns.TITLE_MOVIE,
                    DatabaseContract.FavoriteColumns.POSTER_MOVIE,
                    DatabaseContract.FavoriteColumns.BACKGROUND_MOVIE,
                    DatabaseContract.FavoriteColumns.GENRE_MOVIE,
                    DatabaseContract.FavoriteColumns.DESC_MOVIE,
                    DatabaseContract.FavoriteColumns.RELEASE_MOVIE,
                    DatabaseContract.FavoriteColumns.LANG_MOVIE,
                    DatabaseContract.FavoriteColumns.POPULAR_MOVIE,
                    DatabaseContract.FavoriteColumns.RATING_MOVIE,
                    DatabaseContract.FavoriteColumns.CATEGORY_MOVIE

            );
    private static String DATABASE_NAME = "dbfavoritemovie";

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase mSqLiteDatabase) {
        mSqLiteDatabase.execSQL(SQL_CREATE_TABLE_FAVORITE_MOVIE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase mSqLiteDatabase, int oldVersion, int newVersion) {
        mSqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        onCreate(mSqLiteDatabase);
    }


}
