package edu.calpoly.womangr.mangr;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        // textview to display genres
        final TextView textView = (TextView) findViewById(R.id.preferences_genres_text);

        // call to getGenreList API
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Genre>> call = apiInterface.getGenreList("mangareader.net", API_KEY);
        Log.d(TAG, call.request().url().toString());
        call.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                String genresStr = "";
                List<Genre> genres = response.body();
                for (Genre g : genres) {
                    Log.d(TAG, g.getGenre());
                    genresStr = genresStr + g.getGenre() + ", ";
                }
                genresStr = genresStr.substring(0, genresStr.length() - 2);
                int statusCode = response.code();
                Log.e("Tag", String.valueOf(statusCode));
                textView.setText(genresStr);
            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {
                // Log error here since request failed
                Log.e("Failed", t.toString());
            }
        });

        final EditText editText = (EditText) findViewById(R.id.preferences_genres_edit_text);

        // manGO! button
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent intent =
                                new Intent(PreferenceActivity.this, RecommendationActivity.class);
                        intent.putExtra("preferred_genres", editText.getText().toString());
                        startActivity(intent);
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
                                break;
                            case R.id.tab_dislikes:
                                intent = new Intent(PreferenceActivity.this, DislikesActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.tab_recs:
                                intent = new Intent(PreferenceActivity.this, RecommendationActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.tab_prefs:
                                break;
                        }
                    }
                }
        );
    }
}
