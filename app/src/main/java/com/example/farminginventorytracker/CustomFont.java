package com.example.farminginventorytracker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.Visibility;
import android.os.Bundle;

import android.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.farminginventorytracker.font.FontListAdapter;
import com.example.farminginventorytracker.model.entities.CropTypes;
import com.example.farminginventorytracker.viewmodel.CropViewModel;

import java.util.ArrayList;

public class CustomFont extends Fragment {
    private View view;
    private FontListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_custom_font, container, false);

        // Set up the RecyclerView.
        recyclerView = this.getActivity().findViewById(R.id.recyclerview);
        adapter = new FontListAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setVisibility(View.VISIBLE);

        adapter.setFonts(FontSingleton.getInstance().getMyFontsList());

        adapter.setOnItemClickListener(new FontListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                FontSingleton f = FontSingleton.getInstance();
                f.setMyFont(adapter.getFontAtPosition(position));

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                String CompleteFontString = f.getMyFont();
                String[] parts = CompleteFontString.split("/");
                String fontToSave = parts[1];

                editor.putString(getString(R.string.saved_font), fontToSave);
                editor.apply();

                recyclerView.setVisibility(View.GONE);
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                HomeFragment hf = new HomeFragment();
                loadFragment(hf);
            }
        });

        return view;
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