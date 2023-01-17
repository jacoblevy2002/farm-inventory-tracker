package com.example.farminginventorytracker.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farminginventorytracker.RecyclerClickInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericAdapter<T extends GenericHolder, I> extends RecyclerView.Adapter<T> {
    protected final RecyclerClickInterface rci;
    protected List<I> items;

    public GenericAdapter(RecyclerClickInterface _rci) {
        rci = _rci;
    }

    protected View getViewHolder(@NonNull ViewGroup parent, int type) {
        return LayoutInflater.from(parent.getContext()).inflate(type, parent, false);
    }

    @Override
    public int getItemCount() {
        if (items != null) return items.size();
        else return 0;
    }

    public void setItems(List<I> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<String> getItemsAsStrings() {
        List<String> outp = new ArrayList<>();
        for (I item : items) outp.add(item.toString());
        return outp;
    }

    public I getAtPosition(int pos) {
        return items.get(pos);
    }
}
