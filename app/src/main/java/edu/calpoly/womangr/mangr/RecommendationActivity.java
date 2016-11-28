package edu.calpoly.womangr.mangr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.wenchao.cardstack.CardStack;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.calpoly.womangr.mangr.adapter.CardsDataAdapter;
import edu.calpoly.womangr.mangr.model.Manga;
import edu.calpoly.womangr.mangr.model.MangaByGenre;
import edu.calpoly.womangr.mangr.rest.ApiClient;
import edu.calpoly.womangr.mangr.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendationActivity extends AppCompatActivity {
    private static final String TAG = RecommendationActivity.class.getSimpleName();
    private static final String API_KEY = "ahE5pYl9OfmshytVyaNSJkDIIQCip1dRTSwjsnqMM0cHvvBPUF";
    private CardStack cardStack;
    private CardsDataAdapter cardAdapter;
    public static List<MangaByGenre> mbg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        final Intent intent = getIntent();
        String preferredGenres = intent.getStringExtra("preferred_genres");
        if (preferredGenres == null) preferredGenres = "action";

        Log.d("genres", preferredGenres);

        cardStack = (CardStack) findViewById(R.id.container);
        cardStack.setContentResource(R.layout.card_layout);

        cardAdapter = new CardsDataAdapter(getApplicationContext(), 0);

        // example call to getMangaByGenres API
        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        //final Call<List<MangaByGenre>> call = apiInterface.getMangaByGenres("mangareader.net", preferredGenres, API_KEY);

        final Call<List<MangaByGenre>> call = apiInterface.search("mangareader.net", preferredGenres, API_KEY);

        Log.d(TAG, call.request().url().toString());

        call.enqueue(new Callback<List<MangaByGenre>>() {
            @Override
            public void onResponse(Call<List<MangaByGenre>> call, Response<List<MangaByGenre>> response) {
                List<MangaByGenre> mangasByGenres = response.body();

                for (MangaByGenre m : mangasByGenres) {
                    Call<Manga> mangaCall = apiInterface.getMangaDetails("mangareader.net", m.getMangaId(), API_KEY);

                    mangaCall.enqueue(new Callback<Manga>() {
                        @Override
                        public void onResponse(Call<Manga> call, Response<Manga> response) {
                            Manga manga = response.body();
                            cardAdapter.add(manga);
                        }

                        @Override
                        public void onFailure(Call<Manga> call, Throwable t) {
                            // Log error here since request failed
                            Log.e("Failed", t.toString());
                        }
                    });
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

        cardStack.setAdapter(cardAdapter);
        cardStack.setListener(new CardStackListener());

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

    public Drawable urlToDrawable(String url, String name) {
        try {
            if (url != null) {
                InputStream is = (InputStream)new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, name);
                return d;
            }
        } catch (Exception e) {
            System.out.println("Exc=" + e);
            return null;
        }

        return null;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
