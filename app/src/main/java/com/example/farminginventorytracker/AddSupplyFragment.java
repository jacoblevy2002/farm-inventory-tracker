package com.example.farminginventorytracker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.farminginventorytracker.font.FontChangeCrawler;
import com.example.farminginventorytracker.model.entities.CropTypes;
import com.example.farminginventorytracker.model.entities.Supply;
import com.example.farminginventorytracker.viewmodel.SupplyViewModel;

import java.util.ArrayList;

public class AddSupplyFragment extends Fragment {
    private Object cropRepo;    // TODO: Replace with actual repo class
    private View view;
    private Button addButton;
    private ArrayList<AddSupplyFragment> suppliesToAdd;
    private Spinner supplyTypes;
    private EditText newSupplyType;
    private EditText amount;
    private EditText name;
    private SupplyViewModel viewModel;
    private ArrayList<String> typesList;
    private ArrayList<Long> typesIdList;
    private ArrayList<Supply> supplies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_supply, container, false);

        viewModel = new ViewModelProvider((MainActivity)getActivity()).get(SupplyViewModel.class);

        addButton = view.findViewById(R.id.addSupplyBtn);
        addButton.setOnClickListener(v -> addCrop(v));

        newSupplyType = view.findViewById(R.id.supplyTypeNew);

        amount = view.findViewById(R.id.supplyAmount);

        supplyTypes = view.findViewById(R.id.supplyTypeSpinner);

        // Fill spinner values, taken from https://stackoverflow.com/a/59350097
        typesList = new ArrayList<>();
        typesIdList = new ArrayList<>();
        supplies = new ArrayList<>();

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
        supplyTypes.setAdapter(ctAdapter);
        viewModel.getAllSupplies().observe((MainActivity)getActivity(), ss -> {
            for (Supply s : ss) {
                typesList.add(s.getName());
                typesIdList.add(s.getId());
                supplies.add(s);
            }

            ctAdapter.notifyDataSetChanged();
        });

        return view;
    }

    public void addCrop(View view) {
        try {
            if (newSupplyType.getText().toString().isEmpty()) {
                Long id = typesIdList.get(supplyTypes.getSelectedItemPosition());
                String name = supplyTypes.getSelectedItem().toString();
                Integer count = Integer.valueOf(String.valueOf(amount.getText()));

                for (int i = 0; i < supplies.size(); i++) {
                    if (supplies.get(i).getId() == id){
                        count += supplies.get(i).getStock();
                    }
                }

                viewModel.update(new Supply(id, name, count));
            } else {
                Long id = typesIdList.get(typesIdList.size() - 1) + 1;
                String name = String.valueOf(newSupplyType.getText());
                Integer count = Integer.valueOf(String.valueOf(amount.getText()));

                viewModel.add(new Supply(id, name, count));
            }

            HomeFragment hf = new HomeFragment();
            loadFragment(hf);
        } catch(Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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

    public void removeSupply(int frameID) {

    }

}