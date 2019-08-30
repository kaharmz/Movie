package com.example.root.moviestwo.fragments.favorites;

import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.root.moviestwo.R;
import com.example.root.moviestwo.adapters.favorites.MovieFavoriteAdapter;
import com.example.root.moviestwo.database.MovieHelper;
import com.example.root.moviestwo.entity.Movie;
import com.example.root.moviestwo.interfaces.LoadFavoriteMovieCallback;
import com.example.root.moviestwo.services.LoadMovieFavoriteAsync;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.root.moviestwo.database.MappingHelper.mapCursorToArrayList;


public class MovieFavoriteFragment extends Fragment implements LoadFavoriteMovieCallback {

    private static final String EXTRA_MOVIE = "data_movie";
    private View mView;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MovieFavoriteAdapter mMovieFavoriteAdapter;

    public MovieFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_movie_favorite, container, false);
        initializationViews();
        addMovieList();

        if (savedInstanceState == null) {
            new LoadMovieFavoriteAsync(getContext(), this).execute();
        } else {
            ArrayList<Movie> mList = savedInstanceState.getParcelableArrayList(EXTRA_MOVIE);
            if (mList != null) {
                mMovieFavoriteAdapter.setListFavorite(mList);
            }
        }

        return mView;
    }

    private void addMovieList() {
        addToRecyclerView();
    }

    private void addToRecyclerView() {
        mMovieFavoriteAdapter = new MovieFavoriteAdapter(getActivity());
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }

        mRecyclerView.setAdapter(mMovieFavoriteAdapter);
        mRecyclerView.setHasFixedSize(true);
    }

    private void initializationViews() {
        mProgressBar = mView.findViewById(R.id.progressbar_movie_fav);
        mRecyclerView = mView.findViewById(R.id.recycler_movie_fav);
    }

    @Override
    public void preExecute() {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(Cursor movies) {
        mProgressBar.setVisibility(View.INVISIBLE);
        ArrayList<Movie> mListMovie = mapCursorToArrayList(movies);
        if (mListMovie.size() > 0) {
            mMovieFavoriteAdapter.setListFavorite(mListMovie);
        } else {
            mMovieFavoriteAdapter.setListFavorite(new ArrayList<Movie>());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MovieHelper mMovieHelper = new MovieHelper(getActivity());
        mMovieHelper.close();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (getFragmentManager() != null) {
                getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            }
        }
    }

}






