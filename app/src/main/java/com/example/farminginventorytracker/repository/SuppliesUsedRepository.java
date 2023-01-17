package com.example.farminginventorytracker.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.farminginventorytracker.dao.SuppliesUsedDao;
import com.example.farminginventorytracker.database.FarmDatabase;
import com.example.farminginventorytracker.generic.IRepository;
import com.example.farminginventorytracker.model.entities.SuppliesUsed;

import java.util.List;

public class SuppliesUsedRepository implements IRepository<SuppliesUsed> {
    private final SuppliesUsedDao suppliesUsedDao;
    private final LiveData<List<SuppliesUsed>> allSuppliesUsed;

    public SuppliesUsedRepository(Application application) {
        FarmDatabase database = FarmDatabase.getDatabase(application);
        this.suppliesUsedDao = database.suppliesUsedDao();
        allSuppliesUsed = suppliesUsedDao.getAll();
    }

    @Override
    public LiveData<List<SuppliesUsed>> getAll() {
        return allSuppliesUsed;
    }

    @Override
    public LiveData<SuppliesUsed> getById(long id) {
        return suppliesUsedDao.getById(id);
    }

    public LiveData<List<SuppliesUsed>> getByCropId(long id) {return suppliesUsedDao.getByCropId(id); }

    @Override
    public void update(SuppliesUsed... object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> suppliesUsedDao.update(object));

    }

    @Override
    public void delete(SuppliesUsed object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> suppliesUsedDao.delete(object));

    }

    private long newId;
    @Override
    public long add(SuppliesUsed object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> newId = suppliesUsedDao.insert(object));
        return newId;
    }
}

