package com.example.root.moviestwo.activities;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.root.moviestwo.BuildConfig;
import com.example.root.moviestwo.R;
import com.example.root.moviestwo.database.MovieHelper;
import com.example.root.moviestwo.entity.Movie;

import java.util.List;

import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.BACKGROUND_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.CATEGORY_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.DESC_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.GENRE_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.LANG_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.POPULAR_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.POSTER_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.RATING_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.RELEASE_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.TITLE_MOVIE;


public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MOVIE_POSTER_URL = BuildConfig.BASE_POSTER_URL;
    private static final String MOVIE_BACKGROUND_URL = BuildConfig.BASE_BACKGROUND_URL;
    private static final String EXTRA_MOVIE = "data_movie";
    public List<String> genreMovie;
    MovieHelper mMovieHelper;

    private TextView
            textTitleMovie,
            textGenreMovie,
            textDescMovie,
            textReleaseMovie,
            textLanguageMovie,
            textPopularMovie,
            textRatingMovie;

    private String
            titleMovie,
            descMovie,
            releaseMovie,
            languageMovie,
            popularMovie,
            ratingMovie,
            imagePosterMovie,
            imageBackgroundMovie,
            categoryMovie;

    private ImageView posterMovie, backgroundMovie;
    private Button buttonAddFav;
    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initializationViews();
        getDataMovie();
        buttonAddFav.setOnClickListener(this);
    }

    private void getDataMovie() {
        mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        //Get Data
        if (mMovie != null) {
            titleMovie = mMovie.getTitleMovie();
            genreMovie = mMovie.getGenreMovie();
            descMovie = mMovie.getDescMovie();
            releaseMovie = mMovie.getReleaseMovie();
            languageMovie = mMovie.getLanguageMovie();
            popularMovie = mMovie.getPopularMovie();
            ratingMovie = mMovie.getRatingMovie();
            imagePosterMovie = mMovie.getPosterMovie();
            imageBackgroundMovie = mMovie.getBackgroundMovie();
            categoryMovie = mMovie.getCategoryMovie();

            Glide.with(this).load(MOVIE_POSTER_URL + imagePosterMovie).into(posterMovie);
            Glide.with(this).load(MOVIE_BACKGROUND_URL + imageBackgroundMovie)
                    .apply(new RequestOptions().override(800, 550).centerInside()).into(backgroundMovie);

            //Set Data
            textTitleMovie.setText(titleMovie);
            textGenreMovie.setText(String.valueOf(genreMovie)
                    .replace("[", "").replace("]", ""));
            textDescMovie.setText(descMovie);
            textReleaseMovie.setText(releaseMovie);
            textLanguageMovie.setText(languageMovie);
            textPopularMovie.setText(popularMovie);
            textRatingMovie.setText(ratingMovie);
        }
    }

    private void initializationViews() {
        textTitleMovie = findViewById(R.id.text_title);
        textGenreMovie = findViewById(R.id.text_genre);
        textDescMovie = findViewById(R.id.text_description);
        textReleaseMovie = findViewById(R.id.text_date);
        textLanguageMovie = findViewById(R.id.text_language);
        textPopularMovie = findViewById(R.id.text_popular);
        textRatingMovie = findViewById(R.id.text_rating);
        posterMovie = findViewById(R.id.image_poster);
        backgroundMovie = findViewById(R.id.image_background);
        buttonAddFav = findViewById(R.id.btn_detail_add);
    }

    @Override
    public void onClick(View mView) {

        if (mView.getId() == R.id.btn_detail_add) {
            mMovieHelper = new MovieHelper(this);
            ContentValues values = new ContentValues();
            values.put(TITLE_MOVIE, titleMovie);
            values.put(GENRE_MOVIE, String.valueOf(genreMovie));
            values.put(DESC_MOVIE, descMovie);
            values.put(RELEASE_MOVIE, releaseMovie);
            values.put(LANG_MOVIE, languageMovie);
            values.put(POPULAR_MOVIE, popularMovie);
            values.put(RATING_MOVIE, ratingMovie);
            values.put(CATEGORY_MOVIE, categoryMovie);
            values.put(POSTER_MOVIE, imagePosterMovie);
            values.put(BACKGROUND_MOVIE, imageBackgroundMovie);
            Intent intent = new Intent();
            intent.putExtra(EXTRA_MOVIE, values);
            if (mMovie != null) {
                if (mMovieHelper.rowTitleExists(titleMovie)) {
                    showFailedAlert();
                } else {
                    getContentResolver().insert(CONTENT_URI, values);
                    Toast.makeText(this, " " + titleMovie + " " + getResources().getString(R.string.data_add_success), Toast.LENGTH_SHORT).show();
                }

            }
        }

    }

    private void showFailedAlert() {
        final AlertDialog.Builder mAlert = new AlertDialog.Builder(DetailActivity.this);
        mAlert.setTitle(getResources().getString(R.string.warning_alert));
        mAlert.setMessage("Sorry " + titleMovie + " " + getResources().getString(R.string.warning_alert_data_exists));
        mAlert.setPositiveButton(getResources().getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        mAlert.show();
    }

}