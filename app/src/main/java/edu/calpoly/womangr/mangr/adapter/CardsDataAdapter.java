package edu.calpoly.womangr.mangr.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.calpoly.womangr.mangr.R;
import edu.calpoly.womangr.mangr.sqlite.DatabaseHandler;
import edu.calpoly.womangr.mangr.sqlite.SqlMangaModel;

public class CardsDataAdapter extends ArrayAdapter<String> {

    private TextView title, genres, authors, artists, status, summary;
    private ImageView cover;
    private final DatabaseHandler db;

    public CardsDataAdapter(Context context, int resource) {
        super(context, resource);
        db = new DatabaseHandler(context);
    }

    public CardsDataAdapter(Context context, int resource, List<String> mangaIds) {
        super(context, resource, mangaIds);
        db = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SqlMangaModel manga = db.getManga(getItem(position));

        //Set Title
        title = (TextView)(convertView.findViewById(R.id.title));
        title.setText(manga.getName());

        //Set Cover
        cover = (ImageView) (convertView.findViewById(R.id.manga_cover));
        Picasso.with(cover.getContext())
                .load(manga.getCover())
                .into(cover);

        //Set Genre(s)
        genres = (TextView) (convertView.findViewById(R.id.genres));
        genres.setText(Html.fromHtml("<b>" + "Genre(s): " + "</b>" + manga.getGenres()));

        //Set Author(s)
        authors = (TextView) (convertView.findViewById(R.id.authors));
        authors.setText(Html.fromHtml("<b>" + "Author(s): " + "</b>" + manga.getAuthor()));

        //Set Artist(s)
        artists = (TextView) (convertView.findViewById(R.id.artists));
        artists.setText(Html.fromHtml("<b>" + "Artist(s): " + "</b>" + manga.getArtist()));

        //Set Status
        status = (TextView) (convertView.findViewById(R.id.status));
        String s = manga.getStatus().substring(0, 1).toUpperCase() + manga.getStatus().substring(1);
        status.setText(Html.fromHtml("<b>" + "Status: " + "</b>" + s));

        //Set Summary
        summary = (TextView) (convertView.findViewById(R.id.summary));
        summary.setText(Html.fromHtml("<b>" + "Summary: " + "</b>" + manga.getInfo()));

        return convertView;
    }
}
