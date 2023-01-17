package com.example.farminginventorytracker.repository;
import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.farminginventorytracker.dao.CropTypesDao;
import com.example.farminginventorytracker.dao.SupplyDao;
import com.example.farminginventorytracker.database.FarmDatabase;
import com.example.farminginventorytracker.generic.IRepository;
import com.example.farminginventorytracker.model.entities.CropTypes;

import java.util.List;

public class CropTypesRepository implements IRepository<CropTypes> {
    private final CropTypesDao cropTypesDao;
    private final LiveData<List<CropTypes>> allCropTypes;

    public CropTypesRepository(Application application) {
        FarmDatabase database = FarmDatabase.getDatabase(application);
        this.cropTypesDao = database.cropTypesDao();
        allCropTypes = cropTypesDao.getAll();
    }

    @Override
    public LiveData<List<CropTypes>> getAll() {
        return allCropTypes;
    }

    @Override
    public LiveData<CropTypes> getById(long id) {
        return cropTypesDao.getById(id);
    }

    @Override
    public void update(CropTypes... object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> cropTypesDao.update(object));
    }

    @Override
    public void delete(CropTypes object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> cropTypesDao.delete(object));

    }

    private long newId;
    @Override
    public long add(CropTypes object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> newId = cropTypesDao.insert(object));
        return newId;
    }
}
