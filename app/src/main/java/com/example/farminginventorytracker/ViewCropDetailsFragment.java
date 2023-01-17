package com.example.farminginventorytracker;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farminginventorytracker.font.FontChangeCrawler;
import com.example.farminginventorytracker.model.entities.Crop;
import com.example.farminginventorytracker.model.entities.SuppliesUsed;
import com.example.farminginventorytracker.viewholder.SuppliesUsedAdapter;
import com.example.farminginventorytracker.viewholder.SupplyAdapter;
import com.example.farminginventorytracker.viewmodel.SuppliesUsedViewModel;
import com.example.farminginventorytracker.viewmodel.SupplyViewModel;


public class ViewCropDetailsFragment extends Fragment implements RecyclerClickInterface{

    private Crop crop;
    private SuppliesUsedViewModel viewmodel;
    private SupplyViewModel sViewmodel;
    private RecyclerView recycler;
    private View view;

    @SuppressLint("ValidFragment")
    public ViewCropDetailsFragment(Crop c) {
        // Required empty public constructor
        this.crop = c;
    }

    public ViewCropDetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmodel = new ViewModelProvider((MainActivity)getActivity()).get(SuppliesUsedViewModel.class);
        sViewmodel = new ViewModelProvider((MainActivity)getActivity()).get(SupplyViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_view_crop_details, container, false);

        recycler = view.findViewById(R.id.suppliesList);
        final SuppliesUsedAdapter adapter = new SuppliesUsedAdapter(this);
        final SupplyAdapter sAdapter = new SupplyAdapter(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewmodel.getByCropId(crop.getId()).observe((MainActivity)getActivity(), suppliesUsed -> adapter.setItems(suppliesUsed));
        sViewmodel.getAllSupplies().observe((MainActivity)getActivity(), supplies -> adapter.setSupplyItems(supplies));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        // change fonts of this fragment
        super.onActivityCreated(savedInstanceState);

        FontSingleton f = FontSingleton.getInstance();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getContext().getAssets(), f.getMyFont());
        fontChanger.replaceFonts((ViewGroup) this.getView());
    }

    @Override
    public void onItemClick(int pos) {
        // Do nothing
    }
}