package edu.calpoly.womangr.mangr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import edu.calpoly.womangr.mangr.R;

public class GenresAdapter extends RecyclerView.Adapter<GenreViewHolder> {
    private List<String> genreIds;

    public GenresAdapter(List<String> genreIds) {
        this.genreIds = genreIds;
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
        holder.bind(genreIds.get(position));
    }

    @Override
    public int getItemCount() {
        return genreIds.size();
    }
}
