package edu.calpoly.womangr.mangr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import edu.calpoly.womangr.mangr.model.Manga;
import edu.calpoly.womangr.mangr.model.MangaResults;
import edu.calpoly.womangr.mangr.rest.ApiClient;
import edu.calpoly.womangr.mangr.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreferenceActivity extends AppCompatActivity {

    private String API_KEY = "ahE5pYl9OfmshytVyaNSJkDII";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MangaResults> call = apiInterface.getMangaList("mangareader.net", API_KEY);
        call.enqueue(new Callback<MangaResults>() {
            @Override
            public void onResponse(Call<MangaResults> call, Response<MangaResults> response) {
                List<Manga> mangas = response.body().getResults();
                int statusCode = response.code();
                Log.e("Tag", String.valueOf(statusCode));
            }

            @Override
            public void onFailure(Call<MangaResults> call, Throwable t) {
                // Log error here since request failed
                Log.e("Failed", t.toString());
            }
        });
    }
}
