package com.example.farminginventorytracker.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.farminginventorytracker.model.entities.SuppliesUsed;
import com.example.farminginventorytracker.model.entities.Supply;
import com.example.farminginventorytracker.repository.SuppliesUsedRepository;
import com.example.farminginventorytracker.repository.SupplyRepository;

import java.util.List;

public class SuppliesUsedViewModel extends AndroidViewModel {
    private SuppliesUsedRepository sRepo;

    private LiveData<List<SuppliesUsed>> mAllSupplies;

    public SuppliesUsedViewModel(Application application) {
        super(application);

        sRepo = new SuppliesUsedRepository(application);
        mAllSupplies = sRepo.getAll();
    }

    public LiveData<List<SuppliesUsed>> getByCropId(long id) { return sRepo.getByCropId(id); }

    public LiveData<List<SuppliesUsed>> getAllCrops() { return mAllSupplies; }
}

