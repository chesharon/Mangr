package edu.calpoly.womangr.mangr.rest;

import edu.calpoly.womangr.mangr.model.Chapter;
import edu.calpoly.womangr.mangr.model.Manga;
import edu.calpoly.womangr.mangr.model.MangaByGenreResults;
import edu.calpoly.womangr.mangr.model.MangaResults;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("manga")
    Call<MangaResults> getMangas(@Query("mashape-key") String apiKey);

    // GET MANGA LIST
    @GET("{siteid}")
    Call<MangaResults> getMangaList(@Path("siteid") String siteID, @Query("mashape-key") String apiKey);

    // GET MANGA DETAILS
    @GET("{siteid}/manga/{mangaid}")
    Call<MangaResults> getMangaDetails(@Path("siteid") String siteID, @Path("mangaid") String mangaID, @Query("mashape-key") String apiKey);

    // GET MANGA BY GENRE
    @GET("{siteid}/search/genres/{genreid}")
    Call<MangaByGenreResults> getMangaByGenres(@Path("siteid") String siteID, @Path("genreid") String genreID, @Query("mashape-key") String apiKey);

    //GET CHAPTER
    @GET("{siteid}/manga/{mangaid}/{chapterid}")
    Call<Chapter> getChapter(@Path("siteid") String siteID, @Path("mangaid") String mangaID, @Path("chapterid") int chapterID, @Query("mashape-key") String apiKey);

}