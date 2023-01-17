package com.example.farminginventorytracker.generic;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.farminginventorytracker.model.entities.Crop;
import com.example.farminginventorytracker.model.entities.CropTypes;
import com.example.farminginventorytracker.model.entities.SuppliesUsed;
import com.example.farminginventorytracker.model.entities.Supply;
import com.example.farminginventorytracker.model.entities.Tool;

import java.util.List;

public abstract class GenericDao<T> {
    private final String TABLE_NAME;

    public GenericDao(String TABLE_NAME) {
        this.TABLE_NAME = TABLE_NAME;
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(T object);

    @RawQuery
    protected abstract int deleteAll(SupportSQLiteQuery query);

    public void deleteAll() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("DELETE FROM " + TABLE_NAME);
        deleteAll(query);
    }

    @Delete
    public abstract void delete(T object);

    @RawQuery(observedEntities = Crop.class)
    protected abstract LiveData<List<T>> getAllCrops(SupportSQLiteQuery query);

    @RawQuery(observedEntities = Supply.class)
    protected abstract LiveData<List<T>> getAllSupplies(SupportSQLiteQuery query);

    @RawQuery(observedEntities = Tool.class)
    protected abstract LiveData<List<T>> getAllTools(SupportSQLiteQuery query);

    @RawQuery(observedEntities = CropTypes.class)
    protected abstract LiveData<List<T>> getAllCropTypes(SupportSQLiteQuery query);

    @RawQuery(observedEntities = SuppliesUsed.class)
    protected abstract LiveData<List<T>> getAllSuppliesUsed(SupportSQLiteQuery query);

    public LiveData<List<T>> getAll() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY id ASC");

        switch (TABLE_NAME) {
            case "crop_table":
                return getAllCrops(query);
            case "supply_table":
                return getAllSupplies(query);
            case "tool_table":
                return getAllTools(query);
            case "crop_types_table":
                return getAllCropTypes(query);
            case "supplies_used_table":
                return getAllSuppliesUsed(query);
            default:
                return null;
        }
    }

    @Update
    public abstract void update(T... object);
}
