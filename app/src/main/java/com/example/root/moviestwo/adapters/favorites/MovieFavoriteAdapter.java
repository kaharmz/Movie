package com.example.root.moviestwo.adapters.favorites;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.root.moviestwo.BuildConfig;
import com.example.root.moviestwo.R;
import com.example.root.moviestwo.database.MovieHelper;
import com.example.root.moviestwo.entity.Movie;

import java.util.ArrayList;

public class MovieFavoriteAdapter extends RecyclerView.Adapter<MovieFavoriteAdapter.FavoriteMovieHolder> {

    private static final String MOVIE_POSTER_URL = BuildConfig.BASE_POSTER_URL;

    private ArrayList<Movie> mListFavorite = new ArrayList<>();
    private Activity mActivity;

    public MovieFavoriteAdapter(Activity mActivity) {
        this.mActivity = mActivity;
    }

    private ArrayList<Movie> getListFavorite() {
        return mListFavorite;
    }

    public void setListFavorite(ArrayList<Movie> mList) {
        if (mListFavorite.size() > 0) {
            this.mListFavorite.clear();
        }
        this.mListFavorite.addAll(mList);
        this.notifyDataSetChanged();

    }

    private void deleteItemFavorite(int position) {
        this.mListFavorite.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, mListFavorite.size());
    }

    @NonNull
    @Override
    public FavoriteMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteMovieHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteMovieHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        holder.textTitleFav.setText(getListFavorite().get(position).getTitleMovie());
        holder.textDescFav.setText(getListFavorite().get(position).getDescMovie());
        holder.textReleaseFav.setText(getListFavorite().get(position).getReleaseMovie());
        Glide.with(mActivity).load(MOVIE_POSTER_URL + getListFavorite().get(position).getPosterMovie()).into(holder.imgPosterFav);
        holder.imgButtonDeleteFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movie mMovie = mListFavorite.get(position);
                MovieHelper mMovieHelper = new MovieHelper(mActivity);
                mMovieHelper.deleteProvider(String.valueOf(mMovie.getId()));
                deleteItemFavorite(position);
                Toast.makeText(mActivity, mActivity.getResources().getString(R.string.data_delete_success), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mListFavorite.size();
    }

    class FavoriteMovieHolder extends RecyclerView.ViewHolder {

        TextView textTitleFav, textDescFav, textReleaseFav;
        ImageView imgPosterFav;
        ImageButton imgButtonDeleteFav;

        private FavoriteMovieHolder(@NonNull View itemView) {
            super(itemView);
            textTitleFav = itemView.findViewById(R.id.text_title_fav);
            textDescFav = itemView.findViewById(R.id.text_desc_fav);
            textReleaseFav = itemView.findViewById(R.id.text_date_release_fav);
            imgPosterFav = itemView.findViewById(R.id.image_poster_fav);
            imgButtonDeleteFav = itemView.findViewById(R.id.img_button_delete_fav);
        }
    }
}
