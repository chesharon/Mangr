package edu.calpoly.womangr.mangr.adapter;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import edu.calpoly.womangr.mangr.R;
import edu.calpoly.womangr.mangr.sqlite.DatabaseHandler;
import edu.calpoly.womangr.mangr.sqlite.SqlMangaModel;

import static edu.calpoly.womangr.mangr.DislikesActivity.dislikes_twoPane;
import static edu.calpoly.womangr.mangr.LikesActivity.likes_twoPane;

public class MangaListAdapter extends RecyclerView.Adapter<MangaListAdapter.ViewHolder> {

    private String listType; //likes or dislikes
    private List<SqlMangaModel> mangas;
    private Context context;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private OnItemClickListener listener;

    public MangaListAdapter(String listType, List<SqlMangaModel> mangas, OnItemClickListener listener) {
        this.listType = listType;
        this.mangas = mangas;
        this.listener = listener;
        for (int i = 0; i < mangas.size(); i++) {
            expandState.append(i, false);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SqlMangaModel sqlManga);
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
        holder.setIsRecyclable(false);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(manga);
            }
        });
        holder.title.setText(manga.getName());
        holder.authors.setText(manga.getAuthor());
        holder.artists.setText(manga.getArtist());
        Picasso.with(context)
                .load(manga.getCover())
                .into(holder.cover);
        holder.genres.setText(Html.fromHtml("<b>Genre(s): </b>" + manga.getGenres()));
        holder.status.setText(Html.fromHtml("<b>Status: </b>" + manga.getStatus()));
        holder.summary.setText(Html.fromHtml("<b>Summary: </b>" + manga.getInfo()));

        if (likes_twoPane == false && dislikes_twoPane == false) {
            holder.expandableLayout.setInRecyclerView(true);
            holder.expandableLayout.setInterpolator(Utils.createInterpolator(Utils.ACCELERATE_INTERPOLATOR));
            holder.expandableLayout.setExpanded(expandState.get(position));
            holder.expandableLayout.setListener(new ExpandableLayoutListenerAdapter() {
                @Override
                public void onPreOpen() {
                    createRotateAnimator(holder.arrowDown, 0f, 180f).start();
                    expandState.put(position, true);
                }

                @Override
                public void onPreClose() {
                    createRotateAnimator(holder.arrowDown, 180f, 0f).start();
                    expandState.put(position, false);
                }
            });

            holder.arrowDown.setRotation(expandState.get(position) ? 180f : 0f);
            holder.row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    onClickButton(holder.expandableLayout);
                }
            });
        }
        else {
            holder.arrowDown.setVisibility(View.INVISIBLE);
        }
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
                                                if (listType.equals("likes")) db.deleteLike(manga.getHref());
                                                else if (listType.equals("dislikes")) db.deleteDislike(manga.getHref());

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

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    @Override
    public int getItemCount() {
        return mangas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * You must use the ExpandableLinearLayout in the recycler view.
         * The ExpandableRelativeLayout doesn't work.
         */
        public View view;
        public LinearLayout row;
        public ExpandableLinearLayout expandableLayout;
        public ImageView cover, arrowDown, trashCan;
        public TextView title, authors, artists, genres, status, summary;

        public ViewHolder(View v) {
            super(v);
            view = v;
            cover = (ImageView) v.findViewById(R.id.manga_cover);
            title = (TextView) v.findViewById(R.id.manga_title);
            authors = (TextView) v.findViewById(R.id.manga_authors);
            artists = (TextView) v.findViewById(R.id.manga_artists);
            genres = (TextView) v.findViewById(R.id.expand_genres);
            status = (TextView) v.findViewById(R.id.fragment_status);
            summary = (TextView) v.findViewById(R.id.expand_summary);
            arrowDown = (ImageView) v.findViewById(R.id.arrow_down);
            trashCan = (ImageView) v.findViewById(R.id.trash_can);
            row = (LinearLayout) v.findViewById(R.id.row);
            expandableLayout = (ExpandableLinearLayout) v.findViewById(R.id.expandableLayout);
        }

    }

    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }
}
