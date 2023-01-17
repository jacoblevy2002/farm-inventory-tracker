package com.example.farminginventorytracker.model.entities;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "supply_table")
public class Supply {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private String name;

    @NonNull
    private int stock;

    public Supply(long id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public int getStock() { return stock; }
    public void setStock(int s) { if (s >= 0) stock = s; }
    public String getName() { return name; }
    public void setName(String n) { if (n != null) name = n; }
    public long getId() { return id; }
    public void setId(long i) { if (i > 0) id = i; }

    @Override public String toString() { return getName() + ", with a stock of " + getStock() + "."; }
}
