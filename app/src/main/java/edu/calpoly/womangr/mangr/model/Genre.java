package edu.calpoly.womangr.mangr.model;

import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("genreId")
    private String genre;

    public Genre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
