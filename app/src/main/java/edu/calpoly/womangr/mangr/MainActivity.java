package edu.calpoly.womangr.mangr;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String API_KEY = "ahE5pYl9OfmshytVyaNSJkDII";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*ApiInterface apiInterface = ApiCaller.getClient().create(ApiInterface.class);
        Call<Manga> call = apiInterface.getMangaDetails("mangareader.net", "naruto", API_KEY);
        call.enqueue(new Callback<Manga>() {
            @Override
            public void onResponse(Call<Manga>call, Response<Manga> response) {
                Manga manga = response.body();
                Log.d("Result", "Manga: " + manga);
            }

            @Override
            public void onFailure(Call<Manga>call, Throwable t) {
                // Log error here since request failed
                Log.e("Failed", t.toString());
            }
        });*/
    }
}
