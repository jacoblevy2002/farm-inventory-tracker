package com.example.farminginventorytracker.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.farminginventorytracker.FontSingleton;
import com.example.farminginventorytracker.R;
import com.example.farminginventorytracker.RecyclerClickInterface;
import com.example.farminginventorytracker.font.FontChangeCrawler;

public class SuppliesUsedHolder extends GenericHolder {
    private TextView stock;


    public SuppliesUsedHolder(@NonNull View itemView, @NonNull RecyclerClickInterface _rci) {
        super(itemView, _rci, R.id.supply_container);
        stock = itemView.findViewById(R.id.supply_stock);

        FontSingleton f = FontSingleton.getInstance();
        FontChangeCrawler fontChanger = new FontChangeCrawler(itemView.getContext().getAssets(), f.getMyFont());
        fontChanger.replaceFonts((ViewGroup) itemView);
    }

    public void setStock(Integer _stock) { if (_stock >= 0) stock.setText(_stock.toString()); }
}
