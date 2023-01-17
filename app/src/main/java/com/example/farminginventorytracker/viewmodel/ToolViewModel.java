package com.example.farminginventorytracker.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.farminginventorytracker.model.entities.Tool;
import com.example.farminginventorytracker.repository.ToolRepository;

import java.util.List;

public class ToolViewModel extends AndroidViewModel {
    private ToolRepository tRepo;

    private LiveData<List<Tool>> mAllTools;

    public ToolViewModel(Application application) {
        super(application);

        tRepo = new ToolRepository(application);
        mAllTools = tRepo.getAll();
    }

    public LiveData<List<Tool>> getAllTools() { return mAllTools; }

    public long add(Tool t) { return tRepo.add(t); }

    public void delete(Tool t) { tRepo.delete(t); }

    public void update(Tool t) { tRepo.update(t); }

    public LiveData<Tool> getToolById(long t) { return tRepo.getById(t); }

    public LiveData<Tool> getToolByName(String t) { return tRepo.getByName(t); }


}

