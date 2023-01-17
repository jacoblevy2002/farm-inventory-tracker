package com.example.farminginventorytracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farminginventorytracker.generic.GenericDao;
import com.example.farminginventorytracker.model.entities.Supply;

@Dao
public abstract class SupplyDao extends GenericDao<Supply> {

    public SupplyDao(String TABLE_NAME) {
        super(TABLE_NAME);
    }
    public SupplyDao() { super("supply_table"); }

    @Insert
    public abstract long insert(Supply supply);

    @Delete
    public abstract void delete(Supply supply);

    @Update
    public abstract void update(Supply supply);

    @Query("SELECT * FROM supply_table WHERE id = :id")
    public abstract LiveData<Supply> getById(long id);

    @Query("SELECT * FROM supply_table WHERE name = :name")
    public abstract LiveData<Supply> getByName(String name);

    @Query("SELECT * FROM supply_table LIMIT 1")
    public abstract Supply[] getAny();
}

