package com.example.root.favoritemovies;

import android.database.Cursor;

public interface LoadMovieCallback {
    void postExecute(Cursor mCursor);
}
