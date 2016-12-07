package edu.calpoly.womangr.mangr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import edu.calpoly.womangr.mangr.R;
import edu.calpoly.womangr.mangr.model.Genre;

public class GenresAdapter extends RecyclerView.Adapter<GenreViewHolder> {
    private List<Genre> genres;

    public GenresAdapter(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.genre_item;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GenreViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(GenreViewHolder holder, int position) {
        holder.bind(genres.get(position));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }
}
