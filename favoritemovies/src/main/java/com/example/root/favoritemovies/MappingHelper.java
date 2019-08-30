package com.example.root.favoritemovies;

import android.database.Cursor;

import com.example.root.favoritemovies.entity.FavoriteMovie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.root.favoritemovies.database.DatabaseContract.FavoriteColumns.BACKGROUND_MOVIE;
import static com.example.root.favoritemovies.database.DatabaseContract.FavoriteColumns.CATEGORY_MOVIE;
import static com.example.root.favoritemovies.database.DatabaseContract.FavoriteColumns.DESC_MOVIE;
import static com.example.root.favoritemovies.database.DatabaseContract.FavoriteColumns.GENRE_MOVIE;
import static com.example.root.favoritemovies.database.DatabaseContract.FavoriteColumns.LANG_MOVIE;
import static com.example.root.favoritemovies.database.DatabaseContract.FavoriteColumns.POPULAR_MOVIE;
import static com.example.root.favoritemovies.database.DatabaseContract.FavoriteColumns.POSTER_MOVIE;
import static com.example.root.favoritemovies.database.DatabaseContract.FavoriteColumns.RATING_MOVIE;
import static com.example.root.favoritemovies.database.DatabaseContract.FavoriteColumns.RELEASE_MOVIE;
import static com.example.root.favoritemovies.database.DatabaseContract.FavoriteColumns.TITLE_MOVIE;

public class MappingHelper {
    public static ArrayList<FavoriteMovie> mapCursorToArrayList(Cursor cursor) {
        ArrayList<FavoriteMovie> arrayList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                FavoriteMovie mMovie = new FavoriteMovie();
                mMovie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                mMovie.setTitleMovie(cursor.getString(cursor.getColumnIndexOrThrow(TITLE_MOVIE)));
                mMovie.setPosterMovie(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_MOVIE)));
                mMovie.setBackgroundMovie(cursor.getString(cursor.getColumnIndexOrThrow(BACKGROUND_MOVIE)));
                mMovie.addGenre(cursor.getString(cursor.getColumnIndexOrThrow(GENRE_MOVIE)));
                mMovie.setDescMovie(cursor.getString(cursor.getColumnIndexOrThrow(DESC_MOVIE)));
                mMovie.setReleaseMovie(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_MOVIE)));
                mMovie.setLanguageMovie(cursor.getString(cursor.getColumnIndexOrThrow(LANG_MOVIE)));
                mMovie.setPopularMovie(cursor.getString(cursor.getColumnIndexOrThrow(POPULAR_MOVIE)));
                mMovie.setRatingMovie(cursor.getString(cursor.getColumnIndexOrThrow(RATING_MOVIE)));
                mMovie.setCategoryMovie(cursor.getString(cursor.getColumnIndexOrThrow(CATEGORY_MOVIE)));
                arrayList.add(mMovie);
            }
        }
        return arrayList;
    }
}
