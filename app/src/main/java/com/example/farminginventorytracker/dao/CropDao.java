package com.example.farminginventorytracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farminginventorytracker.generic.GenericDao;
import com.example.farminginventorytracker.model.entities.Crop;

@Dao
public abstract class CropDao extends GenericDao<Crop> {


    public CropDao(String TABLE_NAME) {
        super(TABLE_NAME);
    }
    public CropDao() { super("crop_table"); }

    @Insert
    public abstract long insert(Crop crop);

    @Delete
    public abstract void delete(Crop crop);

    @Update
    public abstract void update(Crop crop);

    @Query("SELECT * FROM crop_table WHERE typeId = :id")
    public abstract LiveData<Crop> getById(long id);

    @Query("SELECT * FROM crop_table LIMIT 1")
    public abstract Crop[] getAny();
}

