package com.example.farminginventorytracker.viewholder;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.farminginventorytracker.FontSingleton;
import com.example.farminginventorytracker.MainActivity;
import com.example.farminginventorytracker.RecyclerClickInterface;
import com.example.farminginventorytracker.font.FontChangeCrawler;
import com.example.farminginventorytracker.model.entities.Supply;
import com.example.farminginventorytracker.R;

public class SupplyAdapter extends GenericAdapter<SupplyHolder, Supply> {

    public SupplyAdapter(RecyclerClickInterface _rci) {
        super(_rci);
    }

    @NonNull
    @Override
    public SupplyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SupplyHolder(getViewHolder(parent, R.layout.supply_item), rci);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplyHolder holder, int position) {
        if (items != null) {
            Supply supply = items.get(position);
            holder.setStock(supply.getStock());
            holder.setType(supply.getName());
        }
    }
}
