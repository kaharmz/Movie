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
import com.example.root.moviestwo.interfaces.LoadFavoriteTvCallback;
import com.example.root.moviestwo.services.LoadTvFavoriteAsync;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.root.moviestwo.database.MappingHelper.mapCursorToArrayList;


public class TvShowFavoriteFragment extends Fragment implements LoadFavoriteTvCallback {

    public static final String EXTRA_MOVIE = "data_movie";
    View mView;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MovieFavoriteAdapter mMovieFavoriteAdapter;

    public TvShowFavoriteFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_movie_favorite, container, false);
        initializationViews();
        addMovieList();

        if (savedInstanceState == null) {
            new LoadTvFavoriteAsync(getContext(), this).execute();
        } else {
            ArrayList<Movie> list = savedInstanceState.getParcelableArrayList(EXTRA_MOVIE);
            if (list != null) {
                mMovieFavoriteAdapter.setListFavorite(list);
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
    public void onDestroy() {
        super.onDestroy();
        MovieHelper mMovieHelper = new MovieHelper(getActivity());
        mMovieHelper.close();
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
    public void postExecute(Cursor tv) {
        mProgressBar.setVisibility(View.INVISIBLE);
        ArrayList<Movie> mListTv = mapCursorToArrayList(tv);
        if (mListTv.size() > 0) {
            mMovieFavoriteAdapter.setListFavorite(mListTv);
        } else {
            mMovieFavoriteAdapter.setListFavorite(new ArrayList<Movie>());
        }
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
