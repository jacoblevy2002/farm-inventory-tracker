package com.example.farminginventorytracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.farminginventorytracker.font.FontChangeCrawler;
import com.example.farminginventorytracker.viewmodel.CropViewModel;
import com.example.farminginventorytracker.viewmodel.SupplyViewModel;
import com.example.farminginventorytracker.viewmodel.ToolViewModel;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment;

    private CropViewModel cropViewModel;
    private SupplyViewModel supplyViewModel;
    private ToolViewModel toolViewModel;

    public ArrayList<String> getFontsList() {
        return fontsList;
    }

    private ArrayList<String> fontsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        loadFragment(homeFragment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FontSingleton f = FontSingleton.getInstance();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String font = sharedPref.getString(getString(R.string.saved_font), "EBGaramond-Bold.ttf");
        f.setMyFont(font);

        // Set font to toolbar
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), f.getMyFont());
        fontChanger.replaceFonts((ViewGroup)toolbar);

        // set up view models
        cropViewModel = new ViewModelProvider(this).get(CropViewModel.class);
        supplyViewModel = new ViewModelProvider(this).get(SupplyViewModel.class);
        toolViewModel = new ViewModelProvider(this).get(ToolViewModel.class);

        // Initialize list of fonts
        fontsList = new ArrayList<>();
        listAssetFiles("fonts");
        FontSingleton.getInstance().setMyFontsList(fontsList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        for (int i=0;i<menu.size();i++) {
            MenuItem mi = menu.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        RecyclerView recyclerView = this.findViewById(R.id.recyclerview);
        recyclerView.setVisibility(View.GONE);

        if (id == R.id.action_add_supply) {
            AddSupplyFragment supplyFragment = new AddSupplyFragment();
            loadFragment(supplyFragment);
            return true;
        }
        else if (id == R.id.action_add_crop){
            AddCropFragment cropFragment = new AddCropFragment();
            loadFragment(cropFragment);
            return true;
        }
        else if(id == R.id.action_add_tool){
            AddToolFragment toolFragment = new AddToolFragment();
            loadFragment(toolFragment);
            return true;
        }
        else if( id == R.id.action_change_font){
            CustomFont customFont = new CustomFont();
            loadFragment(customFont);
            return true;
        }
        else if (id == R.id.action_home) {
            HomeFragment homeFragment = new HomeFragment();
            loadFragment(homeFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void applyFontToMenuItem(MenuItem mi) {
        FontSingleton f = FontSingleton.getInstance();
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), f.getMyFont());

        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , fontChanger.getTypeface()), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
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


    // https://stackoverflow.com/questions/16234529/list-of-files-in-assets-folder-and-its-subfolders
    private boolean listAssetFiles(String path) {

        String [] list;
        try {
            list = getAssets().list(path);
            if (list.length > 0) {
                // This is a folder
                for (String file : list) {
                    if (!listAssetFiles(path + "/" + file))
                        return false;
                    else {
                        // This is a file
                        fontsList.add(file);
                    }
                }
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}