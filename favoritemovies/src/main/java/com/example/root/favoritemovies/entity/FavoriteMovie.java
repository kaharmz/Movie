package com.example.root.favoritemovies.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMovie implements Parcelable {

    public static final Parcelable.Creator<FavoriteMovie> CREATOR = new Parcelable.Creator<FavoriteMovie>() {
        @Override
        public FavoriteMovie createFromParcel(Parcel source) {
            return new FavoriteMovie(source);
        }

        @Override
        public FavoriteMovie[] newArray(int size) {
            return new FavoriteMovie[size];
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

    public FavoriteMovie() {

    }

    protected FavoriteMovie(Parcel in) {
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

