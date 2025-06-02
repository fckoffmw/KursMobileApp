package com.example.kurs.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieDetail {
    private String title;
    private String overview;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("release_date")
    private String releaseDate;

    private int runtime;

    private List<Genre> genres;

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public static class Genre {
        private int id;
        private String name;

        public int getId() { return id; }
        public String getName() { return name; }
    }
}
