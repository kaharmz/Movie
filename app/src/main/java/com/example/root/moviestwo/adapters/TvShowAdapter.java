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
import com.bumptech.glide.request.RequestOptions;
import com.example.root.moviestwo.BuildConfig;
import com.example.root.moviestwo.R;
import com.example.root.moviestwo.entity.Movie;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvViewHolder> {

    private static final String MOVIE_BACKGROUND_URL = BuildConfig.BASE_BACKGROUND_URL;
    private Context context;
    private ArrayList<Movie> mTvItemList;
    private OnItemClickListener mListener;

    public TvShowAdapter(Context context, ArrayList<Movie> mTvItemList) {
        this.context = context;
        this.mTvItemList = mTvItemList;
    }

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tv_show, parent, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder holder, int position) {
        final Movie mMovie = mTvItemList.get(position);
        holder.textTitleMovie.setText(mMovie.getTitleMovie());
        holder.textRatingMovie.setText(mMovie.getRatingMovie());
        holder.textDescMovie.setText(mMovie.getDescMovie());
        Glide.with(context).load(MOVIE_BACKGROUND_URL + mMovie.getBackgroundMovie()).apply(new RequestOptions().override(1000, 500).centerInside()).into(holder.imgBackgroundMovie);
    }

    @Override
    public int getItemCount() {
        return mTvItemList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class TvViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitleMovie, textRatingMovie, textDescMovie;
        private ImageView imgBackgroundMovie;

        private TvViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitleMovie = itemView.findViewById(R.id.text_title_tv);
            textRatingMovie = itemView.findViewById(R.id.text_rating_tv);
            textDescMovie = itemView.findViewById(R.id.text_desc_tv);
            imgBackgroundMovie = itemView.findViewById(R.id.image_tv);
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
