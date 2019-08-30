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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.root.moviestwo.R;
import com.example.root.moviestwo.activities.DetailActivity;
import com.example.root.moviestwo.activities.SearchTvActivity;
import com.example.root.moviestwo.adapters.TvShowAdapter;
import com.example.root.moviestwo.entity.Movie;
import com.example.root.moviestwo.viewmodels.TvShowViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements View.OnClickListener {

    private static final String EXTRA_MOVIE = "data_movie";
    private View mView;
    private RecyclerView mRecyclerView;
    private TvShowAdapter mTvAdapter;
    private ProgressBar mProgressBar;
    private TvShowViewModel mTvShowViewModel;
    private FloatingActionButton mFabSearch;


    public TvShowFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = Objects.requireNonNull(inflater).inflate(R.layout.fragment_tv_show, container, false);
        initializationViews();
        mTvShowViewModel = ViewModelProviders.of(this).get(TvShowViewModel.class);
        mTvShowViewModel.setTvShow();
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

    private void addMovieList() {
        showLoading(true);
        mTvShowViewModel.getTvShow().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Movie> tvShow) {
                if (tvShow != null) {
                    addToRecyclerView(tvShow);
                    showLoading(false);
                    clickItemMovie(tvShow);
                }
            }
        });
    }

    private void clickItemMovie(final ArrayList<Movie> mTvShow) {
        mTvAdapter.setOnItemClickListener(new TvShowAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Movie mTvShows = mTvShow.get(position);
                Intent mIntent = new Intent(getActivity(), DetailActivity.class);
                mIntent.putExtra(EXTRA_MOVIE, mTvShows);
                startActivity(mIntent);
            }
        });
    }

    private void addToRecyclerView(ArrayList<Movie> mTvShow) {
        mTvAdapter = new TvShowAdapter(getActivity(), mTvShow);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        mRecyclerView.setAdapter(mTvAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    private void showLoading(boolean state) {
        if (state) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void initializationViews() {
        mRecyclerView = mView.findViewById(R.id.recycler_tv);
        mProgressBar = mView.findViewById(R.id.progressbar_tv_movie);
        mFabSearch = mView.findViewById(R.id.fab_search_tv);
    }

    @Override
    public void onClick(View mView) {
        if (mView.getId() == R.id.fab_search_tv) {
            Intent mIntent = new Intent(getActivity(), SearchTvActivity.class);
            startActivity(mIntent);
        }
    }
}
