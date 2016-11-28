package edu.calpoly.womangr.mangr.sqlite;

public class SqlMangaModel {
    private String name;
    private String href;
    private String author;
    private String artist;
    private String status;
    private String genres;
    private String info;
    private String cover;

    // Empty constructor
    public SqlMangaModel() {

    }

    // Constructor
    public SqlMangaModel(String name, String href, String author, String artist, String status,
                         String genres, String info, String cover) {
        this.name = name;
        this.href = href;
        this.author = author;
        this.artist = artist;
        this.status = status;
        this.genres = genres;
        this.info = info;
        this.cover = cover;
    }

    public String getHref() {
        return this.href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
