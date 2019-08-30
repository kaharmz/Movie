package com.example.root.favoritemovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.root.favoritemovies.BuildConfig;
import com.example.root.favoritemovies.DetailActivity;
import com.example.root.favoritemovies.R;
import com.example.root.favoritemovies.entity.FavoriteMovie;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends
        RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteViewHolder> {

    private static final String MOVIE_POSTER_URL = BuildConfig.BASE_POSTER_URL;

    private final ArrayList<FavoriteMovie> mListFavorite = new ArrayList<>();
    private final Context context;

    public FavoriteMovieAdapter(Context context) {
        this.context = context;
    }

    public void setListFavorite(ArrayList<FavoriteMovie> mListFavorite) {
        this.mListFavorite.clear();
        this.mListFavorite.addAll(mListFavorite);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_movie, parent, false);
        return new FavoriteViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        final FavoriteMovie favoriteMovie = mListFavorite.get(position);
        holder.textTitle.setText(favoriteMovie.getTitleMovie());
        holder.textGenre.setText(String.valueOf(favoriteMovie.getGenreMovie()).replace("[", "").replace("]", ""));
        holder.textRelease.setText(favoriteMovie.getReleaseMovie());
        holder.textRating.setText(favoriteMovie.getRatingMovie());
        Glide.with(context).load(MOVIE_POSTER_URL + favoriteMovie.getPosterMovie()).into(holder.imgPoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(context, DetailActivity.class);
                mIntent.putExtra("data", favoriteMovie);
                context.startActivity(mIntent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mListFavorite.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        final TextView textTitle, textGenre, textRelease, textRating;
        final ImageView imgPoster;

        public FavoriteViewHolder(@NonNull View mItemView) {
            super(mItemView);
            textTitle = mItemView.findViewById(R.id.favorite_title);
            textGenre = mItemView.findViewById(R.id.favorite_genre);
            textRelease = mItemView.findViewById(R.id.favorite_release);
            textRating = mItemView.findViewById(R.id.favorite_rating);
            imgPoster = mItemView.findViewById(R.id.favorite_image);
        }
    }
}

