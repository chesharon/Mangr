package edu.calpoly.womangr.mangr;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import edu.calpoly.womangr.mangr.model.MangaByGenre;
import edu.calpoly.womangr.mangr.rest.ApiClient;
import edu.calpoly.womangr.mangr.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendationActivity extends AppCompatActivity {
    private static final String TAG = RecommendationActivity.class.getSimpleName();
    private static final String API_KEY = "ahE5pYl9OfmshytVyaNSJkDIIQCip1dRTSwjsnqMM0cHvvBPUF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        final Intent intent = getIntent();
        String preferredGenres = intent.getStringExtra("preferred_genres");
        if (preferredGenres == null) preferredGenres = "action";
        final TextView textView = (TextView) findViewById(R.id.recommendation_text);

        // example call to getMangaByGenres API
        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        final Call<List<MangaByGenre>> call = apiInterface.getMangaByGenres("mangareader.net", preferredGenres, API_KEY);

        Log.d(TAG, call.request().url().toString());

        call.enqueue(new Callback<List<MangaByGenre>>() {
            @Override
            public void onResponse(Call<List<MangaByGenre>> call, Response<List<MangaByGenre>> response) {
                List<MangaByGenre> mangasByGenres = response.body();
                String mangasByGenresStr = "";
                for (MangaByGenre m : mangasByGenres) {
                    Log.d(TAG, m.getName());
                    mangasByGenresStr = mangasByGenresStr + m.getName() + ", ";
                }
                mangasByGenresStr = mangasByGenresStr.substring(0, mangasByGenresStr.length() - 2);
                textView.setText(mangasByGenresStr);
                int statusCode = response.code();
                Log.e("Tag", String.valueOf(statusCode));
            }

            @Override
            public void onFailure(Call<List<MangaByGenre>> call, Throwable t) {
                // Log error here since request failed
                Log.e("Failed", t.toString());
            }
        });


        // Bottom navigation bar
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setDefaultTab(R.id.tab_recs);
        bottomBar.setOnTabSelectListener(
                new OnTabSelectListener() {
                    @Override
                    public void onTabSelected(@IdRes int tabId) {
                        final Intent intent;
                        switch (tabId) {
                            case R.id.tab_likes:
                                intent = new Intent(RecommendationActivity.this, LikesActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.tab_dislikes:
                                intent = new Intent(RecommendationActivity.this, DislikesActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.tab_recs:
                                break;
                            case R.id.tab_prefs:
                                intent = new Intent(RecommendationActivity.this, PreferenceActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                    }
                }
        );
    }
}
