package com.example.root.moviestwo.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.root.moviestwo.R;
import com.example.root.moviestwo.adapters.MovieAdapter;
import com.example.root.moviestwo.entity.Movie;
import com.example.root.moviestwo.viewmodels.search.SearchMovieViewModel;

import java.util.ArrayList;

public class SearchMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "data_movie";
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mProgressBar;
    private SearchMovieViewModel mSearchMovieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        initializationViews();
        mSearchMovieViewModel = ViewModelProviders.of(this).get(SearchMovieViewModel.class);
        addSearchToList();
    }

    private void addSearchToList() {
        mSearchMovieViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Movie> mMovies) {
                if (mMovies != null) {
                    addToRecyclerView(mMovies);
                    showLoading(false);
                    clickItemMovie(mMovies);
                }
            }
        });
    }

    private void clickItemMovie(final ArrayList<Movie> mMovies) {
        mMovieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Movie mMovie = mMovies.get(position);
                Intent mIntent = new Intent(SearchMovieActivity.this, DetailActivity.class);
                mIntent.putExtra(EXTRA_MOVIE, mMovie);
                startActivity(mIntent);
            }
        });
    }

    private void addToRecyclerView(ArrayList<Movie> mMovies) {
        mMovieAdapter = new MovieAdapter(mMovies, this);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    private void initializationViews() {
        mProgressBar = findViewById(R.id.search_movie_progressbar);
        mRecyclerView = findViewById(R.id.search_movie_recycler);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        final SearchManager mSearchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (mSearchManager != null) {
            final SearchView mSearchView = (SearchView) (menu.findItem(R.id.action_search_movie)).getActionView();
            mSearchView.setSearchableInfo(mSearchManager.getSearchableInfo(getComponentName()));
            mSearchView.setQueryHint(getResources().getString(R.string.query_hint_movie));
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearchMovieViewModel.setMovie(query);
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

    public void showLoading(boolean state) {
        if (state) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
