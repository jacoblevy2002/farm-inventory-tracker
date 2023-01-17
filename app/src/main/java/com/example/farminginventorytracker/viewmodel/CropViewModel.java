package com.example.farminginventorytracker.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.farminginventorytracker.model.entities.Crop;
import com.example.farminginventorytracker.model.entities.CropTypes;
import com.example.farminginventorytracker.model.entities.SuppliesUsed;
import com.example.farminginventorytracker.repository.CropRepository;
import com.example.farminginventorytracker.repository.CropTypesRepository;
import com.example.farminginventorytracker.repository.SuppliesUsedRepository;

import java.util.List;

public class CropViewModel extends AndroidViewModel {

    private CropRepository cRepo;
    private CropTypesRepository ctRepo;
    private SuppliesUsedRepository suRepo;

    private LiveData<List<Crop>> mAllCrops;
    private LiveData<List<CropTypes>> mAllCropTypes;
    private LiveData<List<SuppliesUsed>> mAllSuppliesUsed;

    private long latestAddedCropID;

    public CropViewModel(Application application) {
        super(application);

        cRepo = new CropRepository(application);
        ctRepo = new CropTypesRepository(application);
        suRepo = new SuppliesUsedRepository(application);

        mAllCrops = cRepo.getAll();
        mAllCropTypes = ctRepo.getAll();
        mAllSuppliesUsed = suRepo.getAll();

    }

    public LiveData<List<Crop>> getAllCrops() { return mAllCrops; }
    public LiveData<List<CropTypes>> getAllCropTypes() { return mAllCropTypes; }
    public LiveData<List<SuppliesUsed>> getAllSuppliesUsed() { return mAllSuppliesUsed; }

    public long add(Crop c) {    // returns ID of new crop
        return cRepo.add(c);
    }
    public long add(CropTypes ct) {  // returns ID of new crop type
        return ctRepo.add(ct);
    }
    public long add(SuppliesUsed su) { return suRepo.add(su); }

    public void delete(Crop c) { cRepo.delete(c); }
    public void delete(CropTypes ct) { ctRepo.delete(ct); }
    public void delete(SuppliesUsed su) { suRepo.delete(su); }

    public void update(Crop c) { cRepo.update(c); }
    public void update(CropTypes ct) { ctRepo.update(ct); }
    public void update(SuppliesUsed su) { suRepo.update(su); }

    public LiveData<Crop> getCropById(long c) { return cRepo.getById(c); }
    public LiveData<CropTypes> getCropTypeById(long ct) { return ctRepo.getById(ct); }
    public LiveData<SuppliesUsed> getSupplyUsedById(long su) { return suRepo.getById(su); }

    public void addCropWithSuppliesAndType(Crop c, Iterable<SuppliesUsed> s, CropTypes t) { cRepo.addWithSuppliesAndNewType(c, s, t); }
}
