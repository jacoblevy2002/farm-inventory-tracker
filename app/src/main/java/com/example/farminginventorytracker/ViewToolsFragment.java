package com.example.farminginventorytracker;

import android.os.Bundle;

import android.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.farminginventorytracker.model.entities.Tool;
import com.example.farminginventorytracker.viewholder.ToolAdapter;
import com.example.farminginventorytracker.viewmodel.ToolViewModel;

public class ViewToolsFragment extends ViewFragment<ToolAdapter> implements RecyclerClickInterface {
    private View view;
    private ToolViewModel viewmodel;
    private RecyclerView recycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider((MainActivity)getActivity()).get(ToolViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_tools, container, false);

        recycler = view.findViewById(R.id.toolsList);
        adapter = new ToolAdapter(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) { return false; }

                    @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Tool myTool = adapter.getAtPosition(position);
                        Toast.makeText(getActivity(),getString(R.string.delete_preamble) + " " + myTool.getName(), Toast.LENGTH_LONG).show();
                        viewmodel.delete(myTool);
                    }
                });
        helper.attachToRecyclerView(recycler);

        viewmodel.getAllTools().observe((MainActivity)getActivity(), tools -> adapter.setItems(tools));

        return view;
    }

    @Override
    public void onItemClick(int pos) { }    // do nothing
}