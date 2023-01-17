package com.example.farminginventorytracker.generic;

import androidx.lifecycle.LiveData;

import com.example.farminginventorytracker.model.entities.Tool;

import java.util.List;

public interface IRepository<T> {
    LiveData<List<T>> getAll();

    LiveData<T> getById(long id);

    void update(T... object);

    void delete(T object);

    long add(T object);
}