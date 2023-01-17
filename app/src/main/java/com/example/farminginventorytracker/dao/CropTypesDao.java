package com.example.farminginventorytracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farminginventorytracker.generic.GenericDao;
import com.example.farminginventorytracker.model.entities.CropTypes;

@Dao
public abstract class CropTypesDao extends GenericDao<CropTypes> {

    public CropTypesDao(String TABLE_NAME) {
        super(TABLE_NAME);
    }
    public CropTypesDao() { super("crop_types_table"); }

    @Insert
    public abstract long insert(CropTypes cropType);

    @Delete
    public abstract void delete(CropTypes cropType);

    @Update
    public abstract void update(CropTypes cropType);

    @Query("SELECT * FROM crop_types_table WHERE id = :id")
    public abstract LiveData<CropTypes> getById(long id);

    @Query("SELECT * FROM crop_types_table LIMIT 1")
    public abstract CropTypes[] getAny();
}

