package com.example.root.moviestwo.entity;


import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.BACKGROUND_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.CATEGORY_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.DESC_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.GENRE_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.LANG_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.POPULAR_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.POSTER_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.RATING_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.RELEASE_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.TITLE_MOVIE;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.getColumnInt;
import static com.example.root.moviestwo.database.DatabaseContract.FavoriteColumns.getColumnString;

public class Movie implements Parcelable {


    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private String titleMovie;
    private String posterMovie;
    private String backgroundMovie;
    private List<String> genreMovie = new ArrayList<>();
    private String descMovie;
    private String releaseMovie;
    private String languageMovie;
    private String popularMovie;
    private String ratingMovie;
    private String categoryMovie;
    private int id;

    public Movie() {

    }

    public Movie(
            String titleMovie,
            String posterMovie,
            String backgroundMovie,
            String descMovie,
            String releaseMovie,
            String languageMovie,
            String popularMovie,
            String ratingMovie,
            String categoryMovie) {

        this.titleMovie = titleMovie;
        this.posterMovie = posterMovie;
        this.backgroundMovie = backgroundMovie;
        this.genreMovie = new ArrayList<String>();
        this.descMovie = descMovie;
        this.releaseMovie = releaseMovie;
        this.languageMovie = languageMovie;
        this.popularMovie = popularMovie;
        this.ratingMovie = ratingMovie;
        this.categoryMovie = categoryMovie;
    }

    protected Movie(Parcel in) {
        this.titleMovie = in.readString();
        this.posterMovie = in.readString();
        this.backgroundMovie = in.readString();
        this.genreMovie = in.createStringArrayList();
        this.descMovie = in.readString();
        this.releaseMovie = in.readString();
        this.languageMovie = in.readString();
        this.popularMovie = in.readString();
        this.ratingMovie = in.readString();
        this.categoryMovie = in.readString();
        this.id = in.readInt();
    }

    public Movie(Cursor cursor) {
        this.titleMovie = getColumnString(cursor, TITLE_MOVIE);
        this.posterMovie = getColumnString(cursor, POSTER_MOVIE);
        this.backgroundMovie = getColumnString(cursor, BACKGROUND_MOVIE);
        this.addGenre(getColumnString(cursor, GENRE_MOVIE));
        this.descMovie = getColumnString(cursor, DESC_MOVIE);
        this.releaseMovie = getColumnString(cursor, RELEASE_MOVIE);
        this.languageMovie = getColumnString(cursor, LANG_MOVIE);
        this.popularMovie = getColumnString(cursor, POPULAR_MOVIE);
        this.ratingMovie = getColumnString(cursor, RATING_MOVIE);
        this.categoryMovie = getColumnString(cursor, CATEGORY_MOVIE);
        this.id = getColumnInt(cursor, _ID);
    }


    public void addGenre(String mGenre) {
        this.genreMovie.add(mGenre);
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    public void setTitleMovie(String titleMovie) {
        this.titleMovie = titleMovie;
    }

    public String getPosterMovie() {
        return posterMovie;
    }

    public void setPosterMovie(String posterMovie) {
        this.posterMovie = posterMovie;
    }

    public String getBackgroundMovie() {
        return backgroundMovie;
    }

    public void setBackgroundMovie(String backgroundMovie) {
        this.backgroundMovie = backgroundMovie;
    }

    public List<String> getGenreMovie() {
        return genreMovie;
    }

    public void setGenreMovie(List<String> genreMovie) {
        this.genreMovie = genreMovie;
    }

    public String getDescMovie() {
        return descMovie;
    }

    public void setDescMovie(String descMovie) {
        this.descMovie = descMovie;
    }

    public String getReleaseMovie() {
        return releaseMovie;
    }

    public void setReleaseMovie(String releaseMovie) {
        this.releaseMovie = releaseMovie;
    }

    public String getLanguageMovie() {
        return languageMovie;
    }

    public void setLanguageMovie(String languageMovie) {
        this.languageMovie = languageMovie;
    }

    public String getPopularMovie() {
        return popularMovie;
    }

    public void setPopularMovie(String popularMovie) {
        this.popularMovie = popularMovie;
    }

    public String getRatingMovie() {
        return ratingMovie;
    }

    public void setRatingMovie(String ratingMovie) {
        this.ratingMovie = ratingMovie;
    }

    public String getCategoryMovie() {
        return categoryMovie;
    }

    public void setCategoryMovie(String categoryMovie) {
        this.categoryMovie = categoryMovie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.titleMovie);
        dest.writeString(this.posterMovie);
        dest.writeString(this.backgroundMovie);
        dest.writeStringList(this.genreMovie);
        dest.writeString(this.descMovie);
        dest.writeString(this.releaseMovie);
        dest.writeString(this.languageMovie);
        dest.writeString(this.popularMovie);
        dest.writeString(this.ratingMovie);
        dest.writeString(this.categoryMovie);
        dest.writeInt(this.id);
    }

}