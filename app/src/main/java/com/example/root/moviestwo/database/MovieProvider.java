package com.example.root.moviestwo.database;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import static com.example.root.moviestwo.database.DatabaseContract.AUTHORITY;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.TABLE_MOVIE;

@SuppressLint("Registered")
public class MovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 3;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE);
        sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE + "/#", MOVIE_ID);
    }

    private MovieHelper mMovieHelper;

    @Override
    public boolean onCreate() {
        mMovieHelper = MovieHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        mMovieHelper.open();
        SQLiteQueryBuilder mQueryBuilder = new SQLiteQueryBuilder();
        mQueryBuilder.setTables(TABLE_MOVIE);
        DatabaseHelper mDatabaseHelper = new DatabaseHelper(getContext());
        SQLiteDatabase mDatabase = mDatabaseHelper.getReadableDatabase();
        Cursor mCursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                mCursor = mQueryBuilder.query(mDatabase, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MOVIE_ID:
                mCursor = mMovieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                mCursor = null;
                break;

        }
        return mCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues mContentValues) {
        mMovieHelper.open();
        long mAdded;
        if (sUriMatcher.match(uri) == MOVIE) {
            mAdded = mMovieHelper.insertProvider(mContentValues);
        } else {
            mAdded = 0;
        }

        if (mAdded > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }

        return Uri.parse(CONTENT_URI + "/" + mAdded);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] strings) {
        mMovieHelper.open();
        int mDeleted;
        if (sUriMatcher.match(uri) == MOVIE) {
            mDeleted = mMovieHelper.deleteProvider(uri.getLastPathSegment());
        } else {
            mDeleted = 0;
        }

        if (mDeleted > 0) {
            Objects.requireNonNull(getContext()).getContentResolver().notifyChange(uri, null);
        }

        return mDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
