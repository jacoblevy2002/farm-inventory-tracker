package com.example.farminginventorytracker.viewholder;

import androidx.annotation.NonNull;

import com.example.farminginventorytracker.FontSingleton;
import com.example.farminginventorytracker.R;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.farminginventorytracker.RecyclerClickInterface;
import com.example.farminginventorytracker.font.FontChangeCrawler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CropHolder extends GenericHolder {

    private TextView datePlanted;
    // Home RecyclerView doesn't show supplies used; that's only shown on the view crop fragment

    public CropHolder(@NonNull View itemView, @NonNull RecyclerClickInterface _rci) {
        super(itemView, _rci, R.id.crop_container);
        datePlanted = itemView.findViewById(R.id.crop_date);

        FontSingleton f = FontSingleton.getInstance();
        FontChangeCrawler fontChanger = new FontChangeCrawler(itemView.getContext().getAssets(), f.getMyFont());
        fontChanger.replaceFonts((ViewGroup) itemView);
    }

    public void setDate(Date _date) {
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        datePlanted.setText(df.format(_date));
    }
}
