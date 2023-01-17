package com.example.farminginventorytracker.viewholder;

import androidx.annotation.NonNull;

import com.example.farminginventorytracker.FontSingleton;
import com.example.farminginventorytracker.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.farminginventorytracker.RecyclerClickInterface;
import com.example.farminginventorytracker.font.FontChangeCrawler;

public class ToolHolder extends GenericHolder {
    private TextView price;
    private TextView stock;

    public ToolHolder(@NonNull View itemView, @NonNull RecyclerClickInterface _rci) {
        super(itemView, _rci, R.id.tool_container);
        price = itemView.findViewById(R.id.tool_price);
        stock = itemView.findViewById(R.id.tool_stock);

        FontSingleton f = FontSingleton.getInstance();
        FontChangeCrawler fontChanger = new FontChangeCrawler(itemView.getContext().getAssets(), f.getMyFont());
        fontChanger.replaceFonts((ViewGroup) itemView);
    }

    public void setPrice(double _price) {
        if (_price > 0) price.setText(Double.toString(Math.round(_price)));
    }

    public void setStock(Integer _stock) {
        if (_stock >= 0) stock.setText(_stock.toString());
    }
}
