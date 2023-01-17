package com.example.farminginventorytracker;

import android.os.Bundle;

import android.app.Fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.farminginventorytracker.model.entities.Supply;
import com.example.farminginventorytracker.viewholder.SupplyAdapter;
import com.example.farminginventorytracker.viewholder.SupplyHolder;
import com.example.farminginventorytracker.viewmodel.SupplyViewModel;

public class ViewSuppliesFragment extends ViewFragment<SupplyAdapter> implements RecyclerClickInterface {
    private View view;
    private SupplyViewModel viewmodel;
    private RecyclerView recycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider((MainActivity)getActivity()).get(SupplyViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_supplies, container, false);

        recycler = view.findViewById(R.id.suppliesList);
        adapter = new SupplyAdapter(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) { return false; }

                    @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Supply mySupply = adapter.getAtPosition(position);
                        Toast.makeText(getActivity(),getString(R.string.delete_preamble) + " " + mySupply.getName(), Toast.LENGTH_LONG).show();
                        viewmodel.delete(mySupply);
                    }
                });
        helper.attachToRecyclerView(recycler);

        viewmodel.getAllSupplies().observe((MainActivity)getActivity(), supplies -> adapter.setItems(supplies));

        return view;
    }

    @Override
    public void onItemClick(int pos) { }    // do nothing
}