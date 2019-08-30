package com.example.root.moviestwo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.provider.BaseColumns._ID;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.TABLE_MOVIE;

public class MovieHelper {

    private static final String DATABASE_TABLE = TABLE_MOVIE;
    private static DatabaseHelper mDatabaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase mDatabase;

    public MovieHelper(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
    }

    static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    void open() throws SQLException {
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void close() {
        mDatabaseHelper.close();
        if (mDatabase.isOpen())
            mDatabase.close();
    }

    public boolean rowTitleExists(String title) {
        boolean exists;
        mDatabase = mDatabaseHelper.getWritableDatabase();
        String selection = "title = ? ";
        String[] selectionArgs = {title};
        Cursor cursor = mDatabase.query(
                DATABASE_TABLE,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);
        exists = (cursor.getCount() > 0);
        Log.i("Title Exists", "" + exists);
        cursor.close();
        return exists;
    }

    Cursor queryByIdProvider(String id) {
        return mDatabase.query(DATABASE_TABLE
                , null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    long insertProvider(ContentValues mValues) {
        return mDatabase.insert(DATABASE_TABLE, null, mValues);
    }

    public int deleteProvider(String id) {
        return mDatabase.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
