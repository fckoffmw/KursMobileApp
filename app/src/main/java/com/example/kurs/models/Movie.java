package com.example.kurs.models;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {
    private String title;
    private String overview;
    private String poster_path;
    private String release_date;

    private List<String> genres;

    private int duration;

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getDuration() {
        return duration;
    }
    private int id;

    public int getId() {
        return id;
    }

}
