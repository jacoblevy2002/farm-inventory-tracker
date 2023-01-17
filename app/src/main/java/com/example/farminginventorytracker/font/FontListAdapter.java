package com.example.farminginventorytracker.font;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.farminginventorytracker.R;

import java.util.List;

public class FontListAdapter extends RecyclerView.Adapter<FontListAdapter.FontViewHolder> {

    private final LayoutInflater mInflater;
    private List<String> mFonts; // Cached copy of words
    private static ClickListener clickListener;

    public FontListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public FontViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new FontViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FontViewHolder holder, int position) {
        if (mFonts != null) {
            String current = mFonts.get(position);
            holder.fontItemView.setText(current);
        } else {
            // Covers the case of data not being ready yet.
            holder.fontItemView.setText("No fonts");
        }
    }

    /**
     * Associates a list of words with this adapter
     */
    public void setFonts(List<String> fonts) {
        mFonts= fonts;
        notifyDataSetChanged();
    }

    /**
     * getItemCount() is called many times, and when it is first called,
     * mWords has not been updated (means initially, it's null, and we can't return null).
     */
    @Override
    public int getItemCount() {
        if (mFonts != null)
            return mFonts.size();
        else return 0;
    }

    /**
     * Gets the word at a given position.
     * This method is useful for identifying which word
     * was clicked or swiped in methods that handle user events.
     *
     * @param position The position of the word in the RecyclerView
     * @return The word at the given position
     */
    public String getFontAtPosition(int position) {
        return mFonts.get(position);
    }

    class FontViewHolder extends RecyclerView.ViewHolder {
        private final TextView fontItemView;

        private FontViewHolder(View itemView) {
            super(itemView);
            fontItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        FontListAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

}
