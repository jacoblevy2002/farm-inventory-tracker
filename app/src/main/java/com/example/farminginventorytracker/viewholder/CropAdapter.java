package com.example.farminginventorytracker.viewholder;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.farminginventorytracker.R;
import com.example.farminginventorytracker.RecyclerClickInterface;
import com.example.farminginventorytracker.model.entities.Crop;
import com.example.farminginventorytracker.model.entities.CropTypes;
import com.example.farminginventorytracker.viewmodel.CropViewModel;

import java.util.ArrayList;
import java.util.List;

public class CropAdapter extends GenericAdapter<CropHolder, Crop> {
    private List<CropTypes> ctItems;
    private List<Crop> mItems;

    public CropAdapter(RecyclerClickInterface _rci) {
        super(_rci);
    }

    @NonNull
    @Override
    public CropHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CropHolder(getViewHolder(parent, R.layout.crop_item), rci);
    }

    @Override
    public void onBindViewHolder(@NonNull CropHolder holder, int position) {
        if (items != null) {
            mItems = items;
            Crop crop = items.get(position);
            holder.setDate(crop.getDatePlanted());
            if (ctItems != null) {
                long ctID = crop.getTypeId();
                for (CropTypes c : ctItems) {
                    if (ctID == c.getId()) {
                        holder.setType(c.getName());
                        break;
                    }
                }
            } else {
                holder.setType("Problem getting type"); // croptypes cache not set
            }
        }
    }

    public void setCropTypesItems(List<CropTypes> ct) {
        ctItems = ct;
        notifyDataSetChanged();
    }

    public Crop getItem(int pos) {
        return items.get(pos);
    }
    
    @Override public List<String> getItemsAsStrings() {
        List<String> outp = new ArrayList<>();
        boolean noTypes = ctItems == null;
        for (Crop item : items) {
            String type = "";
            if (noTypes) type = "(TYPES UNAVAILABLE)";
            else {
                long ctID = item.getTypeId();
                for (CropTypes c : ctItems) {
                    if (ctID == c.getId()) {
                        type = c.getName();
                        break;
                    }
                }
            }
            outp.add(item.toString().replace("{type}", type));
        }
        return outp;
    }
}
