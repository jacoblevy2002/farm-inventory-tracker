package com.example.farminginventorytracker;

// recycler view click processor, taken from https://youtu.be/7GPUpvcU1FE

// TODO: Needs to be implemented by any fragments using RecyclerViews.
// pos represents the index of the pressed item in the list/array provided to the RV.
public interface RecyclerClickInterface {
    void onItemClick(int pos);
}
