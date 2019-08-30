package com.example.root.favoritemovies;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.root.favoritemovies.entity.FavoriteMovie;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MOVIE_BACKGROUND_URL = BuildConfig.BASE_BACKGROUND_URL;
    private TextView
            textTitleMovie,
            textGenreMovie,
            textDescMovie,
            textReleaseMovie,
            textLanguageMovie,
            textPopularMovie,
            textRatingMovie;

    private ImageView backgroundMovie;

    private Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initializationViews();
        getDataMovie();
        btnShare.setOnClickListener(this);
    }

    private void getDataMovie() {
        FavoriteMovie mFavoriteMovie = getIntent().getParcelableExtra("data");
        textTitleMovie.setText(mFavoriteMovie.getTitleMovie());
        textGenreMovie.setText(String.valueOf(mFavoriteMovie.getGenreMovie())
                .replace("[", "").replace("]", ""));
        textDescMovie.setText(mFavoriteMovie.getDescMovie());
        textReleaseMovie.setText(mFavoriteMovie.getReleaseMovie());
        textLanguageMovie.setText(mFavoriteMovie.getLanguageMovie());
        textPopularMovie.setText(mFavoriteMovie.getPopularMovie());
        textRatingMovie.setText(mFavoriteMovie.getRatingMovie());
        Glide.with(this).load(MOVIE_BACKGROUND_URL + mFavoriteMovie.getBackgroundMovie()).into(backgroundMovie);
    }

    private void initializationViews() {
        textTitleMovie = findViewById(R.id.title_detail);
        textGenreMovie = findViewById(R.id.genre_detail);
        textDescMovie = findViewById(R.id.desc_detail);
        textReleaseMovie = findViewById(R.id.date_detail);
        textLanguageMovie = findViewById(R.id.language_detail);
        textPopularMovie = findViewById(R.id.popular_detail);
        textRatingMovie = findViewById(R.id.rating_detail);
        backgroundMovie = findViewById(R.id.image_detail);
        btnShare = findViewById(R.id.share_detail);
    }

    @Override
    public void onClick(View mView) {
        if (mView.getId() == R.id.share_detail){
            Toast.makeText(getApplicationContext(), "Button Share Was Clicked ", Toast.LENGTH_SHORT).show();
        }
    }
}
