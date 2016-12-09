package edu.calpoly.womangr.mangr.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.calpoly.womangr.mangr.DislikesActivity;
import edu.calpoly.womangr.mangr.LikesActivity;
import edu.calpoly.womangr.mangr.R;
import edu.calpoly.womangr.mangr.sqlite.DatabaseHandler;
import edu.calpoly.womangr.mangr.sqlite.SqlMangaModel;

import static edu.calpoly.womangr.mangr.DislikesActivity.dislikes_twoPane;
import static edu.calpoly.womangr.mangr.LikesActivity.likes_twoPane;

public class MangaListAdapter extends RecyclerView.Adapter<MangaListAdapter.ViewHolder> {

    private String listType; //likes or dislikes
    private List<SqlMangaModel> mangas;
    private Context context;
    private OnItemClickListener listener;

    public MangaListAdapter(String listType, List<SqlMangaModel> mangas, OnItemClickListener listener) {
        this.listType = listType;
        this.mangas = mangas;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(SqlMangaModel sqlManga, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.manga_row_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SqlMangaModel manga = mangas.get(position);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(manga, position);
            }
        });
        holder.title.setText(manga.getName());
        holder.authors.setText(manga.getAuthor());
        holder.artists.setText(manga.getArtist());
        Picasso.with(context)
                .load(manga.getCover())
                .into(holder.cover);

        holder.trashCan.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog alertDialog = new AlertDialog.Builder(context)
                                .setMessage("Delete this item?")
                                .setPositiveButton("Delete",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                DatabaseHandler db = new DatabaseHandler(context);
                                                if (listType.equals("likes")) {
                                                    db.deleteLike(manga.getHref());
                                                    if (likes_twoPane == true) {
                                                        FragmentManager fm = ((LikesActivity) context).getSupportFragmentManager();
                                                        fm.beginTransaction().remove(fm.findFragmentById(R.id.likes_fragment)).commit();
                                                    }
                                                }
                                                else if (listType.equals("dislikes")) {
                                                    db.deleteDislike(manga.getHref());
                                                    if (dislikes_twoPane == true) {
                                                        FragmentManager fm = ((DislikesActivity)context).getSupportFragmentManager();
                                                        fm.beginTransaction().remove(fm.findFragmentById(R.id.dislikes_fragment)).commit();
                                                    }
                                                }

                                                mangas.remove(position);
                                                notifyDataSetChanged();
                                            }
                                        })
                                .setNegativeButton("No", null)
                                .create();
                        alertDialog.show();
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return mangas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public ImageView cover, trashCan;
        public TextView title, authors, artists, genres, status, summary;

        public ViewHolder(View v) {
            super(v);
            view = v;
            cover = (ImageView) v.findViewById(R.id.manga_cover);
            title = (TextView) v.findViewById(R.id.manga_title);
            authors = (TextView) v.findViewById(R.id.manga_authors);
            artists = (TextView) v.findViewById(R.id.manga_artists);
            trashCan = (ImageView) v.findViewById(R.id.trash_can);
        }
    }
}
