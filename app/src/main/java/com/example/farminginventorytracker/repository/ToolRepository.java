package com.example.farminginventorytracker.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.farminginventorytracker.dao.ToolDao;
import com.example.farminginventorytracker.database.FarmDatabase;
import com.example.farminginventorytracker.generic.IRepository;
import com.example.farminginventorytracker.model.entities.Tool;

import java.util.List;

public class ToolRepository implements IRepository<Tool> {
    private final ToolDao toolDao;
    private final LiveData<List<Tool>> allTools;

    public ToolRepository(Application application) {
        FarmDatabase database = FarmDatabase.getDatabase(application);
        this.toolDao = database.toolDao();
        allTools = toolDao.getAll();
    }

    @Override
    public LiveData<List<Tool>> getAll() {
        return allTools;
    }

    @Override
    public LiveData<Tool> getById(long id) {
        return toolDao.getById(id);
    }


    public LiveData<Tool> getByName(String name) { return toolDao.getByName(name); }

    @Override
    public void update(Tool... object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> toolDao.update(object));

    }

    @Override
    public void delete(Tool object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> toolDao.delete(object));

    }

    private long newId;
    @Override
    public long add(Tool object) {
        FarmDatabase.databaseWriteExecutor.execute(() -> newId = toolDao.insert(object));
        return newId;
    }
}

