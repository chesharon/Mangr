package edu.calpoly.womangr.mangr.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import edu.calpoly.womangr.mangr.PreferenceActivity;
import edu.calpoly.womangr.mangr.R;

public class GenreViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private CheckBox checkBox;
    private String genreStr;

    public GenreViewHolder(final View genreView) {
        super(genreView);
        textView = (TextView) genreView.findViewById(R.id.genre_item_text);
        checkBox = (CheckBox) genreView.findViewById(R.id.genre_item_checkbox);
        checkBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        PreferenceActivity preferenceActivity = (PreferenceActivity) genreView.getContext();
                        if (preferenceActivity.selectedGenres.contains(genreStr)) {
                            preferenceActivity.selectedGenres.remove(genreStr);
                        }
                        else {
                            preferenceActivity.selectedGenres.add(genreStr);
                        }
                    }
                }
        );
    }

    public void bind(final String genreStr) {
        this.genreStr = genreStr;
        /*Typeface font = Typeface.createFromAsset(genreView.getContext().getAssets(), "Quicksand-Regular.ttf");
        textView.setTypeface(font);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);*/
        textView.setText(genreStr.substring(0, 1).toUpperCase() + genreStr.substring(1));
    }
}
