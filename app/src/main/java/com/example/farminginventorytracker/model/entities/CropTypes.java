package com.example.farminginventorytracker.model.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "crop_types_table")
public class CropTypes {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;

    @Ignore
    public CropTypes(String name) {
        this.name = name;
    }

    public CropTypes(long id, String name) {
        this(name);
        this.id = id;
    }

    public long getId() { return id; }
    public void setId(long i) { if (i > 0) id = i; }
    public String getName() { return name; }
    public void setName(String n) { if (n != null) name = n; }

    @Override
    public String toString() {
        return getName();
    }
}
