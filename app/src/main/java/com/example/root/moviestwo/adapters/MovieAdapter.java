package com.example.root.moviestwo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.root.moviestwo.BuildConfig;
import com.example.root.moviestwo.R;
import com.example.root.moviestwo.entity.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private static final String MOVIE_POSTER_URL = BuildConfig.BASE_POSTER_URL;
    private OnItemClickListener mListener;
    private ArrayList<Movie> mMovieItemList;
    private Context context;

    public MovieAdapter(ArrayList<Movie> mMovieItemList, Context context) {
        this.mMovieItemList = mMovieItemList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        final Movie mMovie = mMovieItemList.get(position);
        holder.textTitleMovie.setText(mMovie.getTitleMovie());
        holder.textRatingMovie.setText(mMovie.getRatingMovie());
        Glide.with(context).load(MOVIE_POSTER_URL + mMovie.getPosterMovie()).into(holder.imgPosterMovie);
    }

    @Override
    public int getItemCount() {
        return mMovieItemList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitleMovie, textRatingMovie;
        private ImageView imgPosterMovie;

        private MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitleMovie = itemView.findViewById(R.id.text_title);
            imgPosterMovie = itemView.findViewById(R.id.image_poster);
            textRatingMovie = itemView.findViewById(R.id.text_rating_movie);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });

        }

    }

}
