package edu.calpoly.womangr.mangr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.squareup.picasso.Picasso;

import edu.calpoly.womangr.mangr.sqlite.DatabaseHandler;
import edu.calpoly.womangr.mangr.sqlite.SqlMangaModel;

public class MangaDetails extends AppCompatActivity {

    private TextView title, genres, authors, artists, status, summary;
    private ImageView cover;
    private String mangaId, listType;
    private DatabaseHandler db;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_details);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mangaId = intent.getStringExtra("mangaId");
        listType = intent.getStringExtra("listType");
        position = intent.getIntExtra("index", -1);

        if (listType.equals("likes")) {
            getSupportActionBar().setTitle("Likes - Details");
        } else if (listType.equals("dislikes")) {
            getSupportActionBar().setTitle("Dislikes - Details");
        }

        db = new DatabaseHandler(this);
        SqlMangaModel manga = db.getManga(mangaId);

        title = (TextView) findViewById(R.id.title);
        title.setText(manga.getName());

        cover = (ImageView) findViewById(R.id.manga_cover);
        Picasso.with(cover.getContext())
                .load(manga.getCover())
                .into(cover);

        authors = (TextView) findViewById(R.id.authors);
        authors.setText(Html.fromHtml("<b>Authors(s): </b>" + manga.getAuthor()));

        artists = (TextView) findViewById(R.id.artists);
        artists.setText(Html.fromHtml("<b>Artists(s): </b>" + manga.getArtist()));

        genres = (TextView) findViewById(R.id.genres);
        genres.setText(Html.fromHtml("<b>Genre(s): </b>" + manga.getGenres()));

        status = (TextView) findViewById(R.id.status);
        status.setText(Html.fromHtml("<b>Status: </b>" + manga.getStatus()));

        summary = (TextView) findViewById(R.id.summary);
        if (manga.getInfo() == null) {
            summary.setText(Html.fromHtml("<b>" + "Summary: " + "</b> No summary available."));
        } else {
            summary.setText(Html.fromHtml("<b>" + "Summary: " + "</b>" + manga.getInfo()));
        }

        // Bottom navigation bar
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottom_bar);
        if (listType.equals("likes")) {
            bottomBar.setDefaultTab(R.id.tab_likes);
        } else if (listType.equals("dislikes")) {
            bottomBar.setDefaultTab(R.id.tab_dislikes);
        }
        bottomBar.setOnTabSelectListener(
                new OnTabSelectListener() {
                    @Override
                    public void onTabSelected(@IdRes int tabId) {
                        final Intent intent;
                        switch (tabId) {
                            case R.id.tab_likes:
                                if (!(listType.equals("likes"))) {
                                    intent = new Intent(MangaDetails.this, LikesActivity.class);
                                    startActivity(intent);
                                }
                                break;
                            case R.id.tab_dislikes:
                                if (!(listType.equals("dislikes"))) {
                                    intent = new Intent(MangaDetails.this, DislikesActivity.class);
                                    startActivity(intent);
                                }
                                break;
                            case R.id.tab_recs:
                                intent = new Intent(MangaDetails.this, RecommendationActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.tab_prefs:
                                intent = new Intent(MangaDetails.this, PreferenceActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.delete:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setMessage("Delete this item?")
                        .setPositiveButton("Delete",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = null;
                                        if (listType.equals("likes")) {
                                            db.deleteLike(mangaId);
                                            intent = new Intent(MangaDetails.this, LikesActivity.class);
                                        }
                                        else if (listType.equals("dislikes")) {
                                            db.deleteDislike(mangaId);
                                            intent = new Intent(MangaDetails.this, DislikesActivity.class);
                                        }
                                        intent.putExtra("deletedIndex", position);
                                        startActivity(intent);
                                    }
                                })
                        .setNegativeButton("No", null)
                        .create();
                alertDialog.show();
                return super.onOptionsItemSelected(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
