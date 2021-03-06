package edu.calpoly.womangr.mangr;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import edu.calpoly.womangr.mangr.adapter.MangaListAdapter;
import edu.calpoly.womangr.mangr.sqlite.DatabaseHandler;
import edu.calpoly.womangr.mangr.sqlite.SqlMangaModel;

public class LikesActivity extends AppCompatActivity {

    private MangaListAdapter mangaListAdapter;
    public static boolean likes_twoPane = false;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        getSupportActionBar().setTitle("Likes");

        recyclerView = (RecyclerView) findViewById(R.id.manga_likes_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseHandler db = new DatabaseHandler(this);

        if (findViewById(R.id.likes_fragment) != null) {
            likes_twoPane = true;
            updateFragment(null);
        }
        else {
            likes_twoPane = false;
        }

        mangaListAdapter = new MangaListAdapter("likes", db.getAllLikes(), new MangaListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SqlMangaModel sqlManga, int position) {
                if (likes_twoPane == true) {
                    updateFragment(sqlManga);
                }
                else {
                    Intent intent = new Intent(LikesActivity.this, MangaDetails.class);
                    intent.putExtra("mangaId", sqlManga.getMangaId());
                    intent.putExtra("listType", "likes");
                    intent.putExtra("index", position);
                    startActivity(intent);
                }
            }
        });

        recyclerView.setAdapter(mangaListAdapter);

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

        mangaListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int index = getIntent().getIntExtra("deletedIndex", -1);
        if (index != -1) {
            mangaListAdapter.notifyItemRemoved(index);
        }
    }

    public void updateFragment(SqlMangaModel sqlManga) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MangaDetailsFragment fragment = new MangaDetailsFragment();
        if (sqlManga != null) {
            fragment.setManga(sqlManga);
        }
        transaction.replace(R.id.likes_fragment, fragment);

        transaction.commit();
    }
}
