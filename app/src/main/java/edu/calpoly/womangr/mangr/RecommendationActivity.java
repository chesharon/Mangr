package edu.calpoly.womangr.mangr;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.wenchao.cardstack.CardStack;

import java.util.List;

import edu.calpoly.womangr.mangr.adapter.CardsDataAdapter;
import edu.calpoly.womangr.mangr.model.Manga;
import edu.calpoly.womangr.mangr.model.MangaByGenre;
import edu.calpoly.womangr.mangr.rest.ApiClient;
import edu.calpoly.womangr.mangr.rest.ApiInterface;
import edu.calpoly.womangr.mangr.sqlite.DatabaseHandler;
import edu.calpoly.womangr.mangr.sqlite.SqlMangaModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendationActivity extends AppCompatActivity {
    private static final String TAG = RecommendationActivity.class.getSimpleName();
    private static final String API_KEY = "ahE5pYl9OfmshytVyaNSJkDIIQCip1dRTSwjsnqMM0cHvvBPUF";
    private TextView noMoreRecsTextView;
    private CardStack cardStack;
    private CardsDataAdapter cardAdapter;
    private DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        noMoreRecsTextView = (TextView) findViewById(R.id.no_more_recs_textview);
        hideNoRecommendations();

        final Intent intent = getIntent();
        String preferredGenres = intent.getStringExtra("preferred_genres");
        if (preferredGenres != null) { // if starting from intent from PreferencesActivity
            db.deleteAllRecommendations();
        }
        if (preferredGenres == null) { // if starting from bottom tab
            //restore current recommendations from database
            cardAdapter = new CardsDataAdapter(
                    getApplicationContext(), 0, db.getAllRecommendationIds());

            if (db.recommendationsIsEmpty()) showNoRecommendations();
        }

        cardStack = (CardStack) findViewById(R.id.container);
        cardStack.setContentResource(R.layout.card_layout);

        if (cardAdapter == null) {
            cardAdapter = new CardsDataAdapter(getApplicationContext(), 0);

            // call to search API
            final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            final Call<List<MangaByGenre>> call = apiInterface.search("mangareader.net", preferredGenres, API_KEY);

            Log.d(TAG, call.request().url().toString());

            call.enqueue(new Callback<List<MangaByGenre>>() {
                @Override
                public void onResponse(Call<List<MangaByGenre>> call, Response<List<MangaByGenre>> response) {
                    List<MangaByGenre> mangasByGenres = response.body();
                    int numRecs = 0;

                    for (MangaByGenre m : mangasByGenres) {
                        // filter out mangas already liked or disliked
                        if (!(db.hasLike(m.getMangaId()) || db.hasDisLike(m.getMangaId()))) {
                            numRecs++;

                            if (db.hasManga(m.getMangaId())) { // if db already has manga
                                db.addRecommendation(m.getMangaId());
                                cardAdapter.add(m.getMangaId());
                            }
                            else { // else make getMangaDetails API call
                                Call<Manga> mangaCall = apiInterface.getMangaDetails("mangareader.net", m.getMangaId(), API_KEY);

                                mangaCall.enqueue(new Callback<Manga>() {
                                    @Override
                                    public void onResponse(Call<Manga> call, Response<Manga> response) {
                                        Manga manga = response.body();
                                        SqlMangaModel sqlMangaModel = new SqlMangaModel(manga.getName(), manga.getHref(),
                                                formatInfoList(manga.getAuthor()), formatInfoList(manga.getArtist()),
                                                formatString(manga.getStatus()), formatInfoList(manga.getGenres()),
                                                manga.getInfo(), manga.getCover());

                                        db.addManga(sqlMangaModel);
                                        db.addRecommendation(manga.getHref());
                                        cardAdapter.add(manga.getHref());
                                    }

                                    @Override
                                    public void onFailure(Call<Manga> call, Throwable t) {
                                        // Log error here since request failed
                                        Log.e("Failed", t.toString());
                                    }
                                });
                            }
                        }
                    }

                    if (numRecs == 0) showNoRecommendations();

                    int statusCode = response.code();
                    Log.e("Tag", String.valueOf(statusCode));
                }

                @Override
                public void onFailure(Call<List<MangaByGenre>> call, Throwable t) {
                    // Log error here since request failed
                    Log.e("Failed", t.toString());
                }
            });
        }

        cardStack.setAdapter(cardAdapter);
        cardStack.setVisibleCardNum(2);
        cardStack.setListener(
                new CardStack.CardEventListener() {
                    @Override
                    public boolean swipeEnd(int section, float distance) {
                        return (distance > 300) ? true : false;
                    }

                    @Override
                    public boolean swipeStart(int section, float distance) {
                        return false;
                    }

                    @Override
                    public boolean swipeContinue(int section, float distanceX, float distanceY) {
                        return false;
                    }

                    @Override
                    public void discarded(int mIndex, int direction) {
                        //mIndex starts at 1, cardAdapter starts indexing at 0
                        String mangaId = cardAdapter.getItem(mIndex - 1);

                        if (direction == 1 || direction == 3) {
                            db.addLike(mangaId);
                        }
                        else if (direction == 0 || direction == 2) {
                            db.addDislike(mangaId);
                        }
                        db.deleteRecommendation(mangaId);
                        if (db.recommendationsIsEmpty()) showNoRecommendations();
                    }

                    @Override
                    public void topCardTapped() {

                    }
                }
        );

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

    private void showNoRecommendations() {
        noMoreRecsTextView.setText("No mangas to show.");
    }

    private void hideNoRecommendations() {
        noMoreRecsTextView.setText("");
    }

    private String formatString(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private String formatInfoList(List<String> list) {
        String rt = "";
        if (list.size() > 0) {
            for (String s : list) {
                rt += (s.substring(0, 1).toUpperCase() + s.substring(1)) + ", ";
            }
            rt = rt.substring(0, rt.length() - 2);
        }
        return rt;
    }
}
