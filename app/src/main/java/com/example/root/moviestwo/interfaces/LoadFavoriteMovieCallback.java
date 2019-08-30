package com.example.root.moviestwo.interfaces;

import android.database.Cursor;


public interface LoadFavoriteMovieCallback {
    void preExecute();

    void postExecute(Cursor movies);
}
