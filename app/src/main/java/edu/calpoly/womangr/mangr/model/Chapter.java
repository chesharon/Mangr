package edu.calpoly.womangr.mangr.model;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import edu.calpoly.womangr.mangr.model.Page;

public class Chapter {

    @SerializedName("href")
    private String href;
    @SerializedName("name")
    private String name;
    @SerializedName("pages")
    private List<Page> pages = new ArrayList<Page>();

    public Chapter(String href, String name, List<Page> pages) {
        this.href = href;
        this.name = name;
        this.pages = pages;
    }

    public String getHref() {
        return href;
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

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

}