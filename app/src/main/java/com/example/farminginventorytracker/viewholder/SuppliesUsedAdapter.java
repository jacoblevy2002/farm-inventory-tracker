package com.example.farminginventorytracker.viewholder;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.farminginventorytracker.MainActivity;
import com.example.farminginventorytracker.R;
import com.example.farminginventorytracker.RecyclerClickInterface;
import com.example.farminginventorytracker.model.entities.Crop;
import com.example.farminginventorytracker.model.entities.CropTypes;
import com.example.farminginventorytracker.model.entities.SuppliesUsed;
import com.example.farminginventorytracker.model.entities.Supply;
import com.example.farminginventorytracker.viewmodel.SupplyViewModel;

import java.util.List;

public class SuppliesUsedAdapter extends GenericAdapter<SuppliesUsedHolder, SuppliesUsed> {

    private SupplyViewModel viewmodel;
    private List<Supply> sItems;

    public SuppliesUsedAdapter(RecyclerClickInterface _rci) {
        super(_rci);
    }

    @NonNull
    @Override
    public SuppliesUsedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SuppliesUsedHolder(getViewHolder(parent, R.layout.supply_item), rci);
    }

    @Override
    public void onBindViewHolder(@NonNull SuppliesUsedHolder holder, int position) {
        if (items != null) {
            SuppliesUsed supplyUsed = items.get(position);
            holder.setStock(supplyUsed.getQuantity());
            if (sItems != null) {
                long sID = supplyUsed.getSupplyId();
                for (Supply s : sItems) {
                    if (sID == s.getId()) {
                        holder.setType(s.getName());
                        break;
                    }
                }
            } else {
                holder.setType("Problem getting name");
            }
        }
    }
    public void setSupplyItems(List<Supply> s) {
        sItems = s;
        notifyDataSetChanged();
    }

}
