package edu.calpoly.womangr.mangr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.calpoly.womangr.mangr.R;
import edu.calpoly.womangr.mangr.model.Manga;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaViewHolder> {

    private List<Manga> manga;
    private int rowLayout;
    private Context context;


    public static class MangaViewHolder extends RecyclerView.ViewHolder {
        TextView mangaTitle;

        public MangaViewHolder(View v) {
            super(v);
            mangaTitle = (TextView) v.findViewById(R.id.title);
        }
    }

    public MangaAdapter(List<Manga> manga, int rowLayout, Context context) {
        this.manga = manga;
        this.rowLayout = rowLayout;
        this.context = context;

    }

    @Override
    public MangaAdapter.MangaViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MangaViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MangaViewHolder holder, final int position) {
        holder.mangaTitle.setText(manga.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return manga.size();
    }
}