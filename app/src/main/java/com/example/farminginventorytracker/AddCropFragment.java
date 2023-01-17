package com.example.farminginventorytracker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import android.app.FragmentTransaction;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.farminginventorytracker.font.FontChangeCrawler;
import com.example.farminginventorytracker.model.entities.Crop;
import com.example.farminginventorytracker.model.entities.CropTypes;
import com.example.farminginventorytracker.model.entities.SuppliesUsed;
import com.example.farminginventorytracker.viewmodel.CropViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AddCropFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private View view;
    private Button dateButton;
    private ArrayList<AddSupplyToCropFragment> suppliesToAdd;
    private Spinner types;
    private EditText newType;
    private Button addSupplyButton;
    private Button submitButton;
    private Date chosenDate;
    private LinearLayout addSupplyHolder;
    private boolean dateHasChanged;
    private int lastFrameID;
    private CropViewModel viewModel;
    private ArrayList<String> typesList;
    private ArrayList<Long> typesIdList;
    ArrayList<AddSupplyToCropFragment> addedSupplies;

    public AddCropFragment() {
        chosenDate = new Date(1, 1, 1);
        dateHasChanged = false;
        lastFrameID = 5000;
        suppliesToAdd = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_crop, container, false);

        viewModel = new ViewModelProvider((MainActivity)getActivity()).get(CropViewModel.class);

        dateButton = view.findViewById(R.id.addCropAddDateButton);
        dateButton.setOnClickListener(v -> showDatePicker(v));

        submitButton = view.findViewById(R.id.addCropSubmit);
        submitButton.setOnClickListener(v -> addCrop(v));

        addSupplyButton = view.findViewById(R.id.addCropAddSupplyButton);
        addSupplyButton.setOnClickListener(v -> addSupply(v));

        types = view.findViewById(R.id.cropTypes);

        // Fill spinner values, taken from https://stackoverflow.com/a/59350097
        typesList = new ArrayList<>();
        typesIdList = new ArrayList<>();

        //https://stackoverflow.com/questions/5483495/how-to-set-font-custom-font-to-spinner-text-programmatically
        ArrayAdapter<String> ctAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, typesList){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                FontSingleton f = FontSingleton.getInstance();
                FontChangeCrawler fontChanger = new FontChangeCrawler(view.getContext().getAssets(), f.getMyFont());

                ((TextView) v).setTypeface(fontChanger.getTypeface());

                return v;
            }

            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);

                FontSingleton f = FontSingleton.getInstance();
                FontChangeCrawler fontChanger = new FontChangeCrawler(view.getContext().getAssets(), f.getMyFont());

                ((TextView) v).setTypeface(fontChanger.getTypeface());

                return v;
            }
        };
        ctAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(ctAdapter);
        viewModel.getAllCropTypes().observe((MainActivity)getActivity(), cts -> {
            for (CropTypes ct : cts) {
                typesList.add(ct.getName());
                typesIdList.add(ct.getId());
            }

            ctAdapter.notifyDataSetChanged();
        });

        newType = view.findViewById(R.id.addNewCropTypeInput);

        addSupplyHolder = view.findViewById(R.id.addSupplyToCropBox);

        return view;
    }

    public void addCrop(View view) {
        try {
            if (!dateHasChanged) {
                Toast.makeText(getActivity().getApplicationContext(), "Date must be specified!", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<SuppliesUsed> supplies = new ArrayList<>();
            addedSupplies = new ArrayList<>();
            for (AddSupplyToCropFragment s : suppliesToAdd) {
                supplies.add(s.verify());
                addedSupplies.add(s);
            }
            CropTypes ct = null;
            Crop newCrop = new Crop(chosenDate);

            if (newType.getText().toString().isEmpty()) {
                int selectedTypeIndex = types.getSelectedItemPosition();
                long typeID = typesIdList.get(selectedTypeIndex);
                newCrop.setTypeId(typeID);
            } else ct = new CropTypes(newType.getText().toString());

            viewModel.addCropWithSuppliesAndType(newCrop, supplies, ct);

            HomeFragment hf = new HomeFragment();
            loadFragment(hf);
        } catch(Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            // in case of error, undo any changes made to Supply table
            for (AddSupplyToCropFragment s : addedSupplies) s.undo();
        }
    }

    @SuppressLint("ResourceType")
    public void addSupply(View view) {
        lastFrameID++;
        FrameLayout frame = new FrameLayout(getContext());
        frame.setId(lastFrameID);
        addSupplyHolder.addView(frame);
        AddSupplyToCropFragment childFrag = new AddSupplyToCropFragment(frame, addSupplyHolder, suppliesToAdd);
        suppliesToAdd.add(childFrag);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(lastFrameID, childFrag).commit();
    }

    public void showDatePicker(View view) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);

        dpd.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        chosenDate.setMonth(month);
        chosenDate.setYear(year);
        chosenDate.setDate(day);
        dateHasChanged = true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        // change fonts of this fragment
        super.onActivityCreated(savedInstanceState);

        FontSingleton f = FontSingleton.getInstance();
        FontChangeCrawler fontChanger = new FontChangeCrawler(view.getContext().getAssets(), f.getMyFont());
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
}