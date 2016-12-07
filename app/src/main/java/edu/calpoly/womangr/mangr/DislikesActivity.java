package edu.calpoly.womangr.mangr;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import edu.calpoly.womangr.mangr.adapter.MangaListAdapter;
import edu.calpoly.womangr.mangr.sqlite.DatabaseHandler;
import edu.calpoly.womangr.mangr.sqlite.SqlMangaModel;

public class DislikesActivity extends AppCompatActivity {

    static MangaListAdapter a;
    public static boolean dislikes_twoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dislikes);
        getSupportActionBar().setTitle("Dislikes");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.manga_dislikes_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHandler db = new DatabaseHandler(this);

        if (findViewById(R.id.dislikes_fragment) != null) {
            dislikes_twoPane = true;
            updateFragment(null);
        }
        else {
            dislikes_twoPane = false;
        }

        a = new MangaListAdapter("dislikes", db.getAllDislikes(), new MangaListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SqlMangaModel sqlManga) {
                updateFragment(sqlManga);
            }
        });

        recyclerView.setAdapter(a);

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

        a.notifyDataSetChanged();
    }

    public static void notifyDataChanged() {
        a.notifyDataSetChanged();
    }

    public void updateFragment(SqlMangaModel sqlManga) {
        Log.d("creating", " fragment");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MangaDetailsFragment fragment = new MangaDetailsFragment();
        if (sqlManga != null) {
            Log.d("Clicked on a manga", " " + sqlManga.getName());
            fragment.setManga(sqlManga);
        }
        transaction.replace(R.id.dislikes_fragment, fragment);

        transaction.commit();
    }
}
