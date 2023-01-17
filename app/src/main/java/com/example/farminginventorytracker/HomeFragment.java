package com.example.farminginventorytracker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.tabs.TabLayout;

import com.example.farminginventorytracker.font.FontChangeCrawler;

public class HomeFragment extends Fragment {

    protected View mView;
    private SwitchCompat darkModeSwitch;
    private TabLayout tabs;
    private Button shareButton;
    private ViewFragment currDisplay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        this.mView = view;

        darkModeSwitch = view.findViewById(R.id.switchdarkmode);

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            getActivity().setTheme(R.style.Theme_Dark);
            darkModeSwitch.setChecked(true);
        }
        else{
            getActivity().setTheme(R.style.Theme_Light);
            darkModeSwitch.setChecked(false);
        }

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        tabs = view.findViewById(R.id.homeTabs);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        currDisplay = new ViewCropsFragment();
                        loadViewFragment(currDisplay);
                        break;
                    case 1:
                        currDisplay = new ViewSuppliesFragment();
                        loadViewFragment(currDisplay);
                        break;
                    case 2:
                        currDisplay = new ViewToolsFragment();
                        loadViewFragment(currDisplay);
                        break;
                    default:
                        currDisplay = new ViewCropsFragment();
                        loadViewFragment(currDisplay);  // in case of other, load crops
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { onTabSelected(tab); }
        });

        currDisplay = new ViewCropsFragment();
        loadViewFragment(currDisplay);

        shareButton = view.findViewById(R.id.shareButton);
        shareButton.setOnClickListener(v -> {
            StringBuilder textToSend = new StringBuilder();

            if (currDisplay == null) textToSend.append("ERROR GETTING LIST");
            else
                for (int i = 0; i < currDisplay.getRecyclerItems().size(); i++)
                    textToSend.append("- " + currDisplay.getRecyclerItems().get(i).toString() + "\n");

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend.toString());
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        // change fonts of this fragment
        super.onActivityCreated(savedInstanceState);

        FontSingleton f = FontSingleton.getInstance();
        FontChangeCrawler fontChanger = new FontChangeCrawler(mView.getContext().getAssets(), f.getMyFont());
        fontChanger.replaceFonts((ViewGroup) this.getView());
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

    public void loadViewFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.homeFrame, fragment);
        ft.commit();
    }
}