package edu.calpoly.womangr.mangr.rest;

import java.util.List;

import edu.calpoly.womangr.mangr.model.Chapter;
import edu.calpoly.womangr.mangr.model.Genre;
import edu.calpoly.womangr.mangr.model.Manga;
import edu.calpoly.womangr.mangr.model.MangaByGenre;
import edu.calpoly.womangr.mangr.model.MangaList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    // GET MANGA LIST
    @GET("{siteid}")
    Call<List<MangaList>> getMangaList(@Path("siteid") String siteID, @Query("mashape-key") String apiKey);

    //GET GENRE LIST
    @GET("{siteid}/search/genres")
    Call<List<Genre>> getGenreList(@Path("siteid") String siteID, @Query("mashape-key") String apiKey);

    // GET MANGA DETAILS
    @GET("{siteid}/manga/{mangaid}")
    Call<Manga> getMangaDetails(@Path("siteid") String siteID, @Path("mangaid") String mangaID, @Query("mashape-key") String apiKey);

    // GET MANGAS BY GENRE
    @GET("{siteid}/search/genres/{genreid}?cover=1&info=1\"")
    Call<List<MangaByGenre>> getMangaByGenres(@Path("siteid") String siteID, @Path("genreid") String genreID, @Query("mashape-key") String apiKey);

    //GET CHAPTER
    @GET("{siteid}/manga/{mangaid}/{chapterid}")
    Call<Chapter> getChapter(@Path("siteid") String siteID, @Path("mangaid") String mangaID, @Path("chapterid") int chapterID, @Query("mashape-key") String apiKey);

    //SEARCH
    @GET("{siteid}/search?cover=1&info=1&l=200")
    Call<List<MangaByGenre>> search(@Path("siteid") String siteID, @Query("g") String genreIDS, @Query("mashape-key") String apiKey);
}