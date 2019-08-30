package com.example.root.moviestwo.interfaces;

import android.database.Cursor;

public interface LoadFavoriteTvCallback {
    void preExecute();

    void postExecute(Cursor tv);
}
