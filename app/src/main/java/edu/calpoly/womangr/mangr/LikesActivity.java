package edu.calpoly.womangr.mangr;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class LikesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);

        Intent intent = getIntent();


        // Bottom navigation bar
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setDefaultTab(R.id.tab_likes);
        bottomBar.setOnTabSelectListener(
                new OnTabSelectListener() {
                    @Override
                    public void onTabSelected(@IdRes int tabId) {
                        final Intent intent;
                        switch (tabId) {
                            case R.id.tab_likes:
                                break;
                            case R.id.tab_dislikes:
                                intent = new Intent(LikesActivity.this, DislikesActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.tab_recs:
                                intent = new Intent(LikesActivity.this, RecommendationActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.tab_prefs:
                                intent = new Intent(LikesActivity.this, PreferenceActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                    }
                }
        );
    }
}
