package com.example.farminginventorytracker.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.farminginventorytracker.dao.SupplyDao;
import com.example.farminginventorytracker.database.FarmDatabase;
import com.example.farminginventorytracker.generic.IRepository;
import com.example.farminginventorytracker.model.entities.Supply;

import java.util.List;

public class SupplyRepository implements IRepository<Supply> {
    private final SupplyDao supplyDao;
    private final LiveData<List<Supply>> allSupplies;

    public SupplyRepository(Application application) {
        FarmDatabase database = FarmDatabase.getDatabase(application);
        this.supplyDao = database.supplyDao();
        allSupplies = supplyDao.getAll();
    }

    @Override
    public LiveData<List<Supply>> getAll() {
        return allSupplies;
    }

    @Override
    public LiveData<Supply> getById(long id) {
        return supplyDao.getById(id);
    }

    public LiveData<Supply> getByName(String name) { return supplyDao.getByName(name); }

    @Override
    public void update(Supply... object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> supplyDao.update(object));
    }

    @Override
    public void delete(Supply object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> supplyDao.delete(object));

    }

    private long newId;
    @Override
    public long add(Supply object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> newId = supplyDao.insert(object));
        return newId;
    }
}
