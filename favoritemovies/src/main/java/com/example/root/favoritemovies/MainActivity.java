package com.example.root.favoritemovies;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.root.favoritemovies.adapter.FavoriteMovieAdapter;
import com.example.root.favoritemovies.entity.FavoriteMovie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.root.favoritemovies.MappingHelper.mapCursorToArrayList;
import static com.example.root.favoritemovies.database.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoadMovieCallback {

    private FavoriteMovieAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.favorite_recycler);
        mAdapter = new FavoriteMovieAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        HandlerThread mHandlerThread = new HandlerThread("DataObserver");
        mHandlerThread.start();
        Handler mHandler = new Handler(mHandlerThread.getLooper());
        DataObserver mDataObserver = new DataObserver(mHandler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, mDataObserver);
        new loadData(this, this).execute();
    }

    @Override
    public void postExecute(Cursor mCursor) {
        ArrayList<FavoriteMovie> mListFavorite = mapCursorToArrayList(mCursor);
        if (mListFavorite.size() > 0) {
            mAdapter.setListFavorite(mListFavorite);
        } else {
            Toast.makeText(this, "Tidak Ada data saat ini", Toast.LENGTH_SHORT).show();
            mAdapter.setListFavorite(new ArrayList<FavoriteMovie>());
        }
    }

    private class loadData extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> mWeakContext;
        private final WeakReference<LoadMovieCallback> mWeakCallback;

        public loadData(Context mContext, LoadMovieCallback mCallback) {
            this.mWeakContext = new WeakReference<>(mContext);
            this.mWeakCallback = new WeakReference<>(mCallback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return mWeakContext.get().getContentResolver().query(
                    CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mWeakCallback.get().postExecute(cursor);
        }
    }

    private class DataObserver extends ContentObserver {
        final Context mContext;

        public DataObserver(Handler mHandler, Context mContext) {
            super(mHandler);
            this.mContext = mContext;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new loadData(mContext, (MainActivity) mContext).execute();
        }
    }
}
