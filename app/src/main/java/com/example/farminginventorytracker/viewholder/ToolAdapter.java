package com.example.farminginventorytracker.viewholder;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.farminginventorytracker.RecyclerClickInterface;
import com.example.farminginventorytracker.model.entities.Tool;
import com.example.farminginventorytracker.R;

public class ToolAdapter extends GenericAdapter<ToolHolder, Tool> {
    public ToolAdapter(RecyclerClickInterface _rci) {
        super(_rci);
    }

    @NonNull
    @Override
    public ToolHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ToolHolder(getViewHolder(parent, R.layout.tool_item), rci);
    }

    @Override
    public void onBindViewHolder(@NonNull ToolHolder holder, int position) {
        if (items != null) {
            Tool tool = items.get(position);
            holder.setPrice(tool.getPrice());
            holder.setStock(tool.getStock());
            holder.setType(tool.getName());
        }
    }
}
