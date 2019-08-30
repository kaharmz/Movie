package com.example.root.moviestwo.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.root.moviestwo.R;
import com.example.root.moviestwo.adapters.TvShowAdapter;
import com.example.root.moviestwo.entity.Movie;
import com.example.root.moviestwo.viewmodels.search.SearchTvViewModel;

import java.util.ArrayList;

public class SearchTvActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "data_movie";
    private SearchTvViewModel mSearchTvViewModel;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TvShowAdapter mTvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_search);

        mSearchTvViewModel = ViewModelProviders.of(this).get(SearchTvViewModel.class);
        addSearchToList();
        initializationViews();
    }

    private void addSearchToList() {
        mSearchTvViewModel.getTvShow().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Movie> mTvShows) {
                if (mTvShows != null) {
                    addToRecyclerView(mTvShows);
                    showLoading(false);
                    clickItemMovie(mTvShows);
                }
            }
        });
    }

    private void clickItemMovie(final ArrayList<Movie> mTvShows) {
        mTvAdapter.setOnItemClickListener(new TvShowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Movie mTvShow = mTvShows.get(position);
                Intent mIntent = new Intent(SearchTvActivity.this, DetailActivity.class);
                mIntent.putExtra(EXTRA_MOVIE, mTvShow);
                startActivity(mIntent);
            }
        });
    }

    private void addToRecyclerView(ArrayList<Movie> mTvShows) {
        mTvAdapter = new TvShowAdapter(this, mTvShows);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        mRecyclerView.setAdapter(mTvAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    private void initializationViews() {
        mProgressBar = findViewById(R.id.search_tv_progressbar);
        mRecyclerView = findViewById(R.id.search_tv_recycler);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final SearchManager mSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (mSearchManager != null) {
            final SearchView mSearchView = (SearchView) (menu.findItem(R.id.action_search_movie)).getActionView();
            mSearchView.setSearchableInfo(mSearchManager.getSearchableInfo(getComponentName()));
            mSearchView.setQueryHint(getResources().getString(R.string.query_hint_tv));
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearchTvViewModel.setMovie(query);
                    showLoading(true);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    private void showLoading(boolean state) {
        if (state) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }
}
