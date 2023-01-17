package com.example.farminginventorytracker.model.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tool_table")
/*
,
    foreignKeys = {
                @ForeignKey(
                        entity = ToolTypes.class,
                        parentColumns = "id",
                        childColumns = "typeId",
                        onDelete = ForeignKey.CASCADE
                )}
 */
public class Tool {

    @PrimaryKey(autoGenerate = true)
    private long id;

    //@NonNull
    //private long typeId;

    @NonNull
    private String name;

    @NonNull
    private double price;

    @NonNull
    private int stock;

    public Tool(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public double getPrice() { return price; }
    public void setPrice(double p) { if (p > 0) price = p; }
    public int getStock() { return stock; }
    public void setStock(int s) { if (s >= 0) stock = s; }
    public String getName() { return name; }
    public void setName(String n) { if (n != null) name = n; }
    public long getId() { return id; }
    public void setId(long i) { if (i > 0) id = i; }

    @Override public String toString() { return getName() + ", bought for $" + getPrice() + ", with " + getStock() + " in stock."; }
}
