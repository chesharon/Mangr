package edu.calpoly.womangr.mangr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Manga {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("author")
    @Expose
    private List<String> author = new ArrayList<String>();
    @SerializedName("artist")
    @Expose
    private List<String> artist = new ArrayList<String>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("yearOfRelease")
    @Expose
    private int yearOfRelease;
    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<String>();
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;
    @SerializedName("chapters")
    @Expose
    private List<Chapter> chapters = new ArrayList<Chapter>();

    public Manga(String name, String href, List<String> author, List<String> artist, String status,
                    int yearOfRelease, List<String> genres, String info, String cover, String lastUpdate,
                    List<Chapter> chapters) {
        this.name = name;
        this.href = href;
        this.author = author;
        this.artist = artist;
        this.status = status;
        this.yearOfRelease = yearOfRelease;
        this.genres = genres;
        this.info = info;
        this.cover = cover;
        this.lastUpdate = lastUpdate;
        this.chapters = chapters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<String> getArtist() {
        return artist;
    }

    public void setArtist(List<String> artist) {
        this.artist = artist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
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

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

}