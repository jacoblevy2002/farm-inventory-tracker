package com.example.farminginventorytracker.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.farminginventorytracker.model.entities.Crop;
import com.example.farminginventorytracker.model.entities.CropTypes;
import com.example.farminginventorytracker.model.entities.Supply;
import com.example.farminginventorytracker.repository.CropTypesRepository;
import com.example.farminginventorytracker.repository.SuppliesUsedRepository;
import com.example.farminginventorytracker.repository.SupplyRepository;

import java.util.List;

public class SupplyViewModel extends AndroidViewModel {
    private SupplyRepository sRepo;

    private LiveData<List<Supply>> mAllSupplies;

    public SupplyViewModel(Application application) {
        super(application);

        sRepo = new SupplyRepository(application);

        mAllSupplies = sRepo.getAll();
    }

    public LiveData<List<Supply>> getAllSupplies() { return mAllSupplies; }

    public long add(Supply s) { return sRepo.add(s); }

    public void delete(Supply s) { sRepo.delete(s); }

    public void update(Supply s) { sRepo.update(s); }

    public LiveData<Supply> getSupplyById(long s) { return sRepo.getById(s); }

    public LiveData<Supply> getSupplyByName(String s) { return sRepo.getByName(s); }
}
