package com.example.root.moviestwo.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.root.moviestwo.R;
import com.example.root.moviestwo.activities.DetailActivity;
import com.example.root.moviestwo.activities.SearchMovieActivity;
import com.example.root.moviestwo.adapters.MovieAdapter;
import com.example.root.moviestwo.entity.Movie;
import com.example.root.moviestwo.viewmodels.MovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements View.OnClickListener {

    private static final String EXTRA_MOVIE = "data_movie";
    View mView;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mProgressBar;
    private MovieViewModel mMovieViewModel;
    private FloatingActionButton mFabSearch;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = Objects.requireNonNull(inflater).inflate(R.layout.fragment_movie, container, false);
        initializationViews();
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mMovieViewModel.setMovie();
        addMovieList();
        scrollView();
        mFabSearch.setOnClickListener(this);
        return mView;
    }

    private void scrollView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    mFabSearch.hide();
                } else {
                    mFabSearch.show();
                }
                super.onScrolled(mRecyclerView, dx, dy);
            }
        });
    }

    public void addMovieList() {
        showLoading(true);
        mMovieViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Movie> movies) {
                if (movies != null) {
                    addToRecyclerView(movies);
                    showLoading(false);
                    clickItemMovie(movies);
                }
            }
        });
    }

    private void clickItemMovie(final ArrayList<Movie> mMovies) {
        mMovieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Movie mMovie = mMovies.get(position);
                Intent mIntent = new Intent(getActivity(), DetailActivity.class);
                mIntent.putExtra(EXTRA_MOVIE, mMovie);
                startActivity(mIntent);
            }
        });
    }

    private void addToRecyclerView(ArrayList<Movie> mMovies) {
        mMovieAdapter = new MovieAdapter(mMovies, getActivity());

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }
        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    private void initializationViews() {
        mProgressBar = mView.findViewById(R.id.progressbar_movie);
        mRecyclerView = mView.findViewById(R.id.recycler_movie);
        mFabSearch = mView.findViewById(R.id.fab_search_movie);
    }


    private void showLoading(boolean state) {
        if (state) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View mView) {
        if (mView.getId() == R.id.fab_search_movie) {
            Intent mIntent = new Intent(getActivity(), SearchMovieActivity.class);
            startActivity(mIntent);
        }
    }
}
