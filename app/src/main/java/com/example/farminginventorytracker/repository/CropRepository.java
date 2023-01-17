package com.example.farminginventorytracker.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.farminginventorytracker.MainActivity;
import com.example.farminginventorytracker.dao.CropDao;
import com.example.farminginventorytracker.database.FarmDatabase;
import com.example.farminginventorytracker.generic.IRepository;
import com.example.farminginventorytracker.model.entities.Crop;
import com.example.farminginventorytracker.model.entities.CropTypes;
import com.example.farminginventorytracker.model.entities.SuppliesUsed;

import java.util.List;

public class CropRepository implements IRepository<Crop> {
    private final CropDao cropDao;
    private final LiveData<List<Crop>> allCrops;
    private final FarmDatabase db;

    public CropRepository(Application application) {
        db = FarmDatabase.getDatabase(application);
        this.cropDao = db.cropDao();
        allCrops = cropDao.getAll();
    }


    @Override
    public LiveData<List<Crop>> getAll() {
        return allCrops;
    }

    @Override
    public LiveData<Crop> getById(long id) {
        return cropDao.getById(id);
    }

    @Override
    public void update(Crop... object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> cropDao.update(object));

    }

    @Override
    public void delete(Crop object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> cropDao.delete(object));

    }

    private long newId;
    @Override
    public long add(Crop object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> newId = cropDao.insert(object));
        return newId;
    }

    public void addWithSuppliesAndNewType(Crop object, Iterable<SuppliesUsed> supplies, CropTypes type) {
        FarmDatabase.databaseWriteExecutor.execute(() -> {
            db.runInTransaction(() -> {
                long typeID = 0;
                if (type != null) typeID = db.cropTypesDao().insert(type);

                if (typeID != 0) object.setTypeId(typeID);
                long cropID = cropDao.insert(object);

                if (supplies != null) for (SuppliesUsed su : supplies) {
                    su.setCropId(cropID);
                    db.suppliesUsedDao().insert(su);
                }
            });
        });
    }
}
