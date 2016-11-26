package edu.calpoly.womangr.mangr;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class DislikesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dislikes);

        Intent intent = getIntent();


        // Bottom navigation bar
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        bottomBar.setDefaultTab(R.id.tab_dislikes);
        bottomBar.setOnTabSelectListener(
                new OnTabSelectListener() {
                    @Override
                    public void onTabSelected(@IdRes int tabId) {
                        final Intent intent;
                        switch (tabId) {
                            case R.id.tab_likes:
                                intent = new Intent(DislikesActivity.this, LikesActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.tab_dislikes:
                                break;
                            case R.id.tab_recs:
                                intent = new Intent(DislikesActivity.this, RecommendationActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.tab_prefs:
                                intent = new Intent(DislikesActivity.this, PreferenceActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                    }
                }
        );
    }
}
