package com.example.farminginventorytracker;

import android.app.Fragment;

import com.example.farminginventorytracker.viewholder.GenericAdapter;

import java.util.List;

public class ViewFragment<I extends GenericAdapter> extends Fragment {
    protected I adapter;

    public List<String> getRecyclerItems() { return adapter.getItemsAsStrings(); }
}
