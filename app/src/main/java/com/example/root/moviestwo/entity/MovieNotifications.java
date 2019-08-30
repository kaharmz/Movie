package com.example.root.moviestwo.entity;

public class MovieNotifications {
    private int id;
    private String titleMovie;
    private String releaseMovie;

    public MovieNotifications(int id, String titleMovie, String releaseMovie) {
        this.id = id;
        this.titleMovie = titleMovie;
        this.releaseMovie = releaseMovie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    public void setTitleMovie(String titleMovie) {
        this.titleMovie = titleMovie;
    }

    public String getReleaseMovie() {
        return releaseMovie;
    }

    public void setReleaseMovie(String releaseMovie) {
        this.releaseMovie = releaseMovie;
    }
}
