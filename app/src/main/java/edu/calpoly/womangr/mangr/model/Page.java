package edu.calpoly.womangr.mangr.model;

public class Page {

    private int pageId;
    private String url;

    public Page(int pageId, String url) {
        this.pageId = pageId;
        this.url = url;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}