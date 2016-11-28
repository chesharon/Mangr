package edu.calpoly.womangr.mangr;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.calpoly.womangr.mangr.model.Genre;
import edu.calpoly.womangr.mangr.rest.ApiClient;
import edu.calpoly.womangr.mangr.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreferenceActivity extends AppCompatActivity {
    private static final String TAG = PreferenceActivity.class.getSimpleName();
    private static final String API_KEY = "ahE5pYl9OfmshytVyaNSJkDIIQCip1dRTSwjsnqMM0cHvvBPUF";

    private List<Genre> genres = new ArrayList<>();
    public HashSet<String> selectedGenres = new HashSet<>();
    public GenresAdapter genresAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        // genres grid
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.genres_grid);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new GridLayoutManager(
                PreferenceActivity.this,
                getResources().getInteger(R.integer.genres_num_columns)));
        genresAdapter = new GenresAdapter(genres);
        recyclerView.setAdapter(genresAdapter);

        // call to getGenreList API
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Genre>> call = apiInterface.getGenreList("mangareader.net", API_KEY);
        Log.d(TAG, call.request().url().toString());
        call.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                genres.addAll(response.body());
                assert genres != null;
                genresAdapter.notifyDataSetChanged();
                int statusCode = response.code();
                Log.e(TAG, String.valueOf(statusCode));
            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {
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
                        intent.putExtra("preferred_genres", formatGenreString());
                        Log.d(TAG, formatGenreString());
                        startActivity(intent);
                        finish();
                    }
                }
        );

        // Bottom navigation bar
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setDefaultTab(R.id.tab_prefs);
        bottomBar.setOnTabSelectListener(
                new OnTabSelectListener() {
                    @Override
                    public void onTabSelected(@IdRes int tabId) {
                        final Intent intent;
                        switch (tabId) {
                            case R.id.tab_likes:
                                intent = new Intent(PreferenceActivity.this, LikesActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.tab_dislikes:
                                intent = new Intent(PreferenceActivity.this, DislikesActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.tab_recs:
                                intent = new Intent(PreferenceActivity.this, RecommendationActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.tab_prefs:
                                break;
                        }
                    }
                }
        );
    }

    public String formatGenreString() {
        String genres = "[";
        for (String s : selectedGenres) {
            genres += '"' + s + '"'  + ",";
        }
        if (genres.length() > 0) {
            genres = genres.substring(0, genres.length() - 1);
        }
        genres += "]";

        return genres;
    }
}
