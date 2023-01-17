package com.example.farminginventorytracker.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farminginventorytracker.R;
import com.example.farminginventorytracker.RecyclerClickInterface;

public abstract class GenericHolder extends RecyclerView.ViewHolder {
    protected RecyclerClickInterface rci;
    protected ConstraintLayout layout;
    protected TextView type;

    public GenericHolder(@NonNull View itemView, @NonNull RecyclerClickInterface _rci, int layoutId) {
        super(itemView);
        rci = _rci;
        type = itemView.findViewById(R.id.type);
        layout = itemView.findViewById(layoutId);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) rci.onItemClick(pos);
            }
        });
    }

    public void setType(String _type) { type.setText(_type); }
}
