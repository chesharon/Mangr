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
import edu.calpoly.womangr.mangr.sqlite.SqlMangaModel;

public class CardsDataAdapter extends ArrayAdapter<SqlMangaModel> {

    private TextView title, genres, authors, artists, status, summary;
    private ImageView cover;

    public CardsDataAdapter(Context context, int resource) {
        super(context, resource);
    }

    public CardsDataAdapter(Context context, int resource, List<SqlMangaModel> mangas) {
        super(context, resource, mangas);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Set Title
        title = (TextView)(convertView.findViewById(R.id.title));
        title.setText(getItem(position).getName());

        //Set Cover
        cover = (ImageView) (convertView.findViewById(R.id.manga_cover));
        Picasso.with(cover.getContext())
                .load(getItem(position).getCover())
                .into(cover);

        //Set Genre(s)
        genres = (TextView) (convertView.findViewById(R.id.genres));
        genres.setText(Html.fromHtml("<b>" + "Genre(s): " + "</b>" + getItem(position).getGenres()));

        //Set Author(s)
        authors = (TextView) (convertView.findViewById(R.id.authors));
        authors.setText(Html.fromHtml("<b>" + "Author(s): " + "</b>" + getItem(position).getAuthor()));

        //Set Artist(s)
        artists = (TextView) (convertView.findViewById(R.id.artists));
        artists.setText(Html.fromHtml("<b>" + "Artist(s): " + "</b>" + getItem(position).getArtist()));

        //Set Status
        status = (TextView) (convertView.findViewById(R.id.status));
        String s = getItem(position).getStatus().substring(0, 1).toUpperCase() + getItem(position).getStatus().substring(1);
        status.setText(Html.fromHtml("<b>" + "Status: " + "</b>" + s));

        //Set Summary
        summary = (TextView) (convertView.findViewById(R.id.summary));
        summary.setText(Html.fromHtml("<b>" + "Summary: " + "</b>" + getItem(position).getInfo()));

        return convertView;
    }
}
