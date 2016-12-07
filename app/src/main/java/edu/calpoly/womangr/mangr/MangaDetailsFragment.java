package edu.calpoly.womangr.mangr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import edu.calpoly.womangr.mangr.sqlite.SqlMangaModel;

public class MangaDetailsFragment extends Fragment {

    private ImageView cover;
    private TextView title, authors, artists, genres, status, summary;
    private SqlMangaModel manga;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View linearLayout = null;

        if (manga != null) {
            linearLayout = inflater.inflate(R.layout.card_layout, container, false);

            title = (TextView) linearLayout.findViewById(R.id.title);
            title.setText(manga.getName());

            cover = (ImageView) linearLayout.findViewById(R.id.manga_cover);
            Picasso.with(cover.getContext())
                    .load(manga.getCover())
                    .into(cover);

            authors = (TextView) linearLayout.findViewById(R.id.authors);
            authors.setText(Html.fromHtml("<b>Authors(s): </b>" + manga.getAuthor()));

            artists = (TextView) linearLayout.findViewById(R.id.artists);
            artists.setText(Html.fromHtml("<b>Artists(s): </b>" + manga.getArtist()));

            genres = (TextView) linearLayout.findViewById(R.id.genres);
            genres.setText(Html.fromHtml("<b>Genre(s): </b>" + manga.getGenres()));

            status = (TextView) linearLayout.findViewById(R.id.status);
            status.setText(Html.fromHtml("<b>Status: </b>" + manga.getStatus()));

            summary = (TextView) linearLayout.findViewById(R.id.summary);
            if (manga.getInfo() == null) {
                summary.setText(Html.fromHtml("<b>" + "Summary: " + "</b> No summary available."));
            } else {
                summary.setText(Html.fromHtml("<b>" + "Summary: " + "</b>" + manga.getInfo()));
            }
        }

        return linearLayout;
    }

    public void setManga(SqlMangaModel manga) {
        this.manga = manga;
    }
}
