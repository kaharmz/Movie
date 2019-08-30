package com.example.root.moviestwo.services;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.example.root.moviestwo.interfaces.LoadFavoriteTvCallback;

import java.lang.ref.WeakReference;

import static android.provider.BaseColumns._ID;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.BACKGROUND_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.CATEGORY_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.DESC_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.GENRE_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.LANG_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.POPULAR_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.POSTER_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.RATING_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.RELEASE_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.TITLE_MOVIE;

public class LoadTvFavoriteAsync extends AsyncTask<Void, Void, Cursor> {
    private final WeakReference<Context> mWeakContext;
    private final WeakReference<LoadFavoriteTvCallback> mWeakCallback;

    public LoadTvFavoriteAsync(Context mContext, LoadFavoriteTvCallback mWeakCallback) {
        this.mWeakContext = new WeakReference<>(mContext);
        this.mWeakCallback = new WeakReference<>(mWeakCallback);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mWeakCallback.get().preExecute();
    }

    @Override
    protected void onPostExecute(Cursor tv) {
        super.onPostExecute(tv);
        mWeakCallback.get().postExecute(tv);
    }

    @Override
    protected Cursor doInBackground(Void... voids) {
        Context mContext = mWeakContext.get();
        String[] projection = new String[]{
                _ID,
                TITLE_MOVIE,
                POSTER_MOVIE,
                BACKGROUND_MOVIE,
                GENRE_MOVIE,
                DESC_MOVIE,
                RELEASE_MOVIE,
                LANG_MOVIE,
                POPULAR_MOVIE,
                RATING_MOVIE,
                CATEGORY_MOVIE};

        return mContext.getContentResolver().query(
                CONTENT_URI,
                projection,
                CATEGORY_MOVIE + " = ?",
                new String[]{String.valueOf(2)},
                null,
                null);

    }
}
