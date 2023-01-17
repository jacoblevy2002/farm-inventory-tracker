package com.example.farminginventorytracker.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farminginventorytracker.generic.GenericDao;
import com.example.farminginventorytracker.model.entities.SuppliesUsed;

import java.util.List;

@Dao
public abstract class SuppliesUsedDao extends GenericDao<SuppliesUsed> {

    public SuppliesUsedDao(String TABLE_NAME) {
        super(TABLE_NAME);
    }
    public SuppliesUsedDao() { super("supplies_used_table"); }

    @Insert
    public abstract long insert(SuppliesUsed supplyUsed);

    @Delete
    public abstract void delete(SuppliesUsed supplyUsed);

    @Update
    public abstract void update(SuppliesUsed supplyUsed);

    @Query("SELECT * FROM supplies_used_table WHERE id = :id")
    public abstract LiveData<SuppliesUsed> getById(long id);

    @Query("SELECT * FROM supplies_used_table LIMIT 1")
    public abstract SuppliesUsed[] getAny();

    @Query("SELECT * FROM supplies_used_table WHERE cropId = :id")
    public abstract LiveData<List<SuppliesUsed>> getByCropId(long id);
}

