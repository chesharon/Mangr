package edu.calpoly.womangr.mangr;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import edu.calpoly.womangr.mangr.model.MangaByGenre;
import edu.calpoly.womangr.mangr.rest.ApiClient;
import edu.calpoly.womangr.mangr.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreferenceActivity extends AppCompatActivity {

    private String API_KEY = "ahE5pYl9OfmshytVyaNSJkDIIQCip1dRTSwjsnqMM0cHvvBPUF";
    private static final String TAG = PreferenceActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        // example call to getMangaByGenres API
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<MangaByGenre>> call = apiInterface.getMangaByGenres("mangareader.net", "action", API_KEY);

        Log.d(TAG, call.request().url().toString());

        call.enqueue(new Callback<List<MangaByGenre>>() {
            @Override
            public void onResponse(Call<List<MangaByGenre>> call, Response<List<MangaByGenre>> response) {
                List<MangaByGenre> mangasByGenres = response.body();
                for (MangaByGenre m : mangasByGenres) {
                    Log.d(TAG, m.getName());
                }
                int statusCode = response.code();
                Log.e("Tag", String.valueOf(statusCode));
            }

            @Override
            public void onFailure(Call<List<MangaByGenre>> call, Throwable t) {
                // Log error here since request failed
                Log.e("Failed", t.toString());
            }
        });

        // manGO! button
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent =
                                new Intent(PreferenceActivity.this, RecommendationActivity.class);
                        startActivity(intent);
                    }
                }
        );

        // Bottom navigation bar
        final BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        final Intent intent;

                        switch (item.getItemId()) {
                            case R.id.action_likes:
                                intent = new Intent(PreferenceActivity.this, LikesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_dislikes:
                                intent = new Intent(PreferenceActivity.this, DislikesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.action_rec:
                                intent = new Intent(PreferenceActivity.this, RecommendationActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
    }
}
