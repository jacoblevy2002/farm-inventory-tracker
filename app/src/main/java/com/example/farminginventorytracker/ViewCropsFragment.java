package com.example.farminginventorytracker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import com.example.farminginventorytracker.model.entities.Crop;
import com.example.farminginventorytracker.viewholder.CropAdapter;
import com.example.farminginventorytracker.viewholder.SupplyAdapter;
import com.example.farminginventorytracker.viewmodel.CropViewModel;

public class ViewCropsFragment extends ViewFragment<CropAdapter> implements RecyclerClickInterface {
    private View view;
    private CropViewModel viewmodel;
    private RecyclerView recycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider((MainActivity)getActivity()).get(CropViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_crops, container, false);

        recycler = view.findViewById(R.id.cropsList);
        adapter = new CropAdapter(this);
        recycler.setAdapter(adapter);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) { return false; }

                    @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Crop myCrop = adapter.getAtPosition(position);
                        Toast.makeText(getActivity(),getString(R.string.delete_preamble) + " crop", Toast.LENGTH_LONG).show();
                        viewmodel.delete(myCrop);
                    }
                });
        helper.attachToRecyclerView(recycler);

        viewmodel.getAllCrops().observe((MainActivity)getActivity(), crops -> adapter.setItems(crops));
        viewmodel.getAllCropTypes().observe((MainActivity)getActivity(), ct -> adapter.setCropTypesItems(ct));

        return view;
    }

    @Override
    public void onItemClick(int pos) {

        CropAdapter a = (CropAdapter) recycler.getAdapter();
        // View detailed info
        System.out.println(pos);
        ViewCropDetailsFragment cropDetailsFragment = new ViewCropDetailsFragment(a.getItem(pos));
        loadFragment(cropDetailsFragment);
    }

    public void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}

