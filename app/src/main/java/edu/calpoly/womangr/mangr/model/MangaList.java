package edu.calpoly.womangr.mangr.model;

import com.google.gson.annotations.SerializedName;

public class MangaList {

    @SerializedName("mangaId")
    private String mangaId;
    @SerializedName("name")
    private String name;
    @SerializedName("info")
    private String info;
    @SerializedName("cover")
    private String cover;

    public MangaList(String mangaId, String name, String info, String cover) {
            this.mangaId = mangaId;
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
