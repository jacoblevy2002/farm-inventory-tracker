package com.example.farminginventorytracker.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.farminginventorytracker.generic.GenericDao;
import com.example.farminginventorytracker.model.entities.Supply;
import com.example.farminginventorytracker.model.entities.Tool;

@Dao
public abstract class ToolDao extends GenericDao<Tool> {

    public ToolDao(String TABLE_NAME) {
        super(TABLE_NAME);
    }
    public ToolDao() { super("tool_table"); }

    @Insert
    public abstract long insert(Tool tool);

    @Delete
    public abstract void delete(Tool tool);

    @Update
    public abstract void update(Tool tool);

    @Query("SELECT * FROM tool_table WHERE id = :id")
    public abstract LiveData<Tool> getById(long id);

    @Query("SELECT * FROM tool_table WHERE name = :name")
    public abstract LiveData<Tool> getByName(String name);

    @Query("SELECT * FROM tool_table LIMIT 1")
    public abstract Supply[] getAny();
}

