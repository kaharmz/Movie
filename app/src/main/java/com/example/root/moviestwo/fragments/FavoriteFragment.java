package com.example.root.moviestwo.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.root.moviestwo.R;
import com.example.root.moviestwo.adapters.ViewPageAdapter;
import com.example.root.moviestwo.fragments.favorites.MovieFavoriteFragment;
import com.example.root.moviestwo.fragments.favorites.TvShowFavoriteFragment;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_favorite, container, false);
        TabLayout mTabLayout = mView.findViewById(R.id.tab_layout_fav);
        ViewPager mViewPager = mView.findViewById(R.id.viewpager_fav);
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
        return mView;

    }

    private void setupViewPager(ViewPager mViewPager) {
        ViewPageAdapter mAdapter = new ViewPageAdapter(getChildFragmentManager());
        mAdapter.AddFragment(new MovieFavoriteFragment(), getResources().getString(R.string.tab_title_movie));
        mAdapter.AddFragment(new TvShowFavoriteFragment(), getResources().getString(R.string.tab_title_tv_show));
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
