package edu.calpoly.womangr.mangr.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MangaByGenre {

    @SerializedName("mangaId")
    private String mangaId;
    @SerializedName("name")
    private String name;
    @SerializedName("genres")
    private List<String> genres = new ArrayList<String>();
    @SerializedName("info")
    private String info;
    @SerializedName("cover")
    private String cover;

    public MangaByGenre(String mangaId, String name, List<String> genres, String info, String cover) {
        this.mangaId = mangaId;
        this.name = name;
        this.genres = genres;
        this.info = info;
        this.cover = cover;
    }

    public String getMangaId() {
        return mangaId;
    }

    public void setMangaId(String mangaId) {
        this.mangaId = mangaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
