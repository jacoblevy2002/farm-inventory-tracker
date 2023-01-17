package com.example.farminginventorytracker;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.farminginventorytracker.font.FontChangeCrawler;
import com.example.farminginventorytracker.model.entities.SuppliesUsed;
import com.example.farminginventorytracker.model.entities.Supply;
import com.example.farminginventorytracker.viewmodel.CropViewModel;
import com.example.farminginventorytracker.viewmodel.SupplyViewModel;

import java.util.ArrayList;

public class AddSupplyToCropFragment extends Fragment {
    private Spinner types;
    private EditText amount;
    private View view;
    private Button xButton;
    private FrameLayout frame;
    private LinearLayout holder;
    private ArrayList<AddSupplyToCropFragment> supplies;
    private CropViewModel cropViewModel;
    private SupplyViewModel supplyViewModel;
    private ArrayList<String> typesList;
    private ArrayList<Supply> suppliesList;

    public AddSupplyToCropFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AddSupplyToCropFragment(FrameLayout frame, LinearLayout holder, ArrayList<AddSupplyToCropFragment> supplies) {
        this.frame = frame;
        this.holder = holder;
        this.supplies = supplies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_supply_to_crop, container, false);

        cropViewModel = new ViewModelProvider((MainActivity)getActivity()).get(CropViewModel.class);
        supplyViewModel = new ViewModelProvider((MainActivity)getActivity()).get(SupplyViewModel.class);


        NotificationChannel channel = new NotificationChannel("supply", "Supply Used", NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);

        types = view.findViewById(R.id.addSupplyToCropType);
        // Fill spinner values, taken from https://stackoverflow.com/a/59350097
        typesList = new ArrayList<>();
        suppliesList = new ArrayList<>();
        ArrayAdapter<String> sAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, typesList);
        sAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(sAdapter);
        supplyViewModel.getAllSupplies().observe((MainActivity)getActivity(), sts -> {
            for (Supply s : sts) {
                typesList.add(s.getName());
                suppliesList.add(s);
            }

            sAdapter.notifyDataSetChanged();
        });

        amount = view.findViewById(R.id.addSupplyToCropAmount);

        xButton = view.findViewById(R.id.addSupplyToCropXButton);
        xButton.setOnClickListener(v -> close(v));

        return view;
    }

    private int amountUsed;
    private Supply selectedSupply;

    public SuppliesUsed verify() throws Exception {
        if (amount.getText().toString().isEmpty()) {
            throw new Exception("Supply used amount cannot be empty!");
        }
        amountUsed = Integer.parseInt(amount.getText().toString());

        int selectedSupplyIndex = types.getSelectedItemPosition();
        selectedSupply = suppliesList.get(selectedSupplyIndex);
        if (selectedSupply.getStock() >= amountUsed) {
            selectedSupply.setStock(selectedSupply.getStock() - amountUsed);
            supplyViewModel.update(selectedSupply);

            if(selectedSupply.getStock() <= 10) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "supply")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Warning: Low Supply")
                        .setContentText("You used " + amountUsed + " " + selectedSupply.getName() + " and have " + selectedSupply.getStock() + " left.")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
                notificationManager.notify(1, builder.build());
            }
        } else throw new Exception("There is not enough " + selectedSupply.getName() + " stock available!");

        return new SuppliesUsed(selectedSupply.getId(), amountUsed);
    }

    public void undo() {
        selectedSupply.setStock(selectedSupply.getStock() + amountUsed);
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

    public void close(View v) {
        holder.removeView(frame);
        supplies.remove(this);
    }
}