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

import edu.calpoly.womangr.mangr.R;
import edu.calpoly.womangr.mangr.model.Manga;

public class CardsDataAdapter extends ArrayAdapter<Manga> {

    private TextView title, genres, authors, artists, status, summary;
    private ImageView cover;

    public CardsDataAdapter(Context context, int resource) {
        super(context, resource);
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
        String g = "";
        for (String s : getItem(position).getGenres()) {
            g += (s.substring(0, 1).toUpperCase() + s.substring(1));
            g += ", ";
        }
        if (g.length() > 0) {
            g = g.substring(0, g.length() - 2);
        }
        genres.setText(Html.fromHtml("<b>" + "Genre(s): " + "</b>" + g));

        //Set Author(s)
        authors = (TextView) (convertView.findViewById(R.id.authors));
        String a = "";
        for (String s : getItem(position).getAuthor()) {
            a += (s.substring(0, 1).toUpperCase() + s.substring(1));
            a += ", ";
        }
        if (a.length() > 0) {
            a = a.substring(0, a.length() - 2);
        }
        authors.setText(Html.fromHtml("<b>" + "Author(s): " + "</b>" + a));

        //Set Artist(s)
        artists = (TextView) (convertView.findViewById(R.id.artists));
        String ar = "";
        for (String s : getItem(position).getArtist()) {
            ar += (s.substring(0, 1).toUpperCase() + s.substring(1));
            ar += ", ";
        }
        if (ar.length() > 0) {
            ar = ar.substring(0, ar.length() - 2);
        }
        artists.setText(Html.fromHtml("<b>" + "Artist(s): " + "</b>" + ar));

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
