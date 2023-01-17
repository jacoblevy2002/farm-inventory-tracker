package com.example.farminginventorytracker;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.farminginventorytracker.font.FontChangeCrawler;
import com.example.farminginventorytracker.model.entities.Tool;
import com.example.farminginventorytracker.viewmodel.ToolViewModel;

import java.util.ArrayList;


public class AddToolFragment extends Fragment {

    private ToolViewModel viewModel;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_tool, container, false);

        viewModel = new ViewModelProvider((MainActivity)getActivity()).get(ToolViewModel.class);

        Button addBtn = view.findViewById(R.id.addToolBtn);
        addBtn.setOnClickListener(v -> addTool(v));
        
        return view;

    }

    private void addTool(View v) {

        try
        {
            EditText price = view.findViewById(R.id.toolPrice);

            EditText name = view.findViewById(R.id.toolName);

            EditText stock = view.findViewById(R.id.toolStock);

            viewModel.add(new Tool(name.getText().toString(), Double.parseDouble(price.getText().toString()), Integer.parseInt(stock.getText().toString())));


            HomeFragment homeFragment = new HomeFragment();
            loadFragment(homeFragment);


        }
        catch(Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


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

    public void loadFragment(android.app.Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }
}