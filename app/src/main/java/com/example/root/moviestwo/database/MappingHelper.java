package com.example.root.moviestwo.database;

import android.database.Cursor;

import com.example.root.moviestwo.entity.Movie;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor cursor) {
        ArrayList<Movie> arrayList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Movie mMovie = new Movie();
                mMovie.setId(cursor.getInt(0));
                mMovie.setTitleMovie(cursor.getString(1));
                mMovie.setPosterMovie(cursor.getString(2));
                mMovie.setBackgroundMovie(cursor.getString(3));
                mMovie.addGenre(cursor.getString(4));
                mMovie.setDescMovie(cursor.getString(5));
                mMovie.setReleaseMovie(cursor.getString(6));
                mMovie.setLanguageMovie(cursor.getString(7));
                mMovie.setPopularMovie(cursor.getString(8));
                mMovie.setRatingMovie(cursor.getString(9));
                mMovie.setCategoryMovie(cursor.getString(10));
                arrayList.add(mMovie);
            }
        }
        return arrayList;
    }

}
