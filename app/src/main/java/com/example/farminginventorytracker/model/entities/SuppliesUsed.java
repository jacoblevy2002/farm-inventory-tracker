package com.example.farminginventorytracker.model.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "supplies_used_table",
        foreignKeys = {
                @ForeignKey(
                        entity = Crop.class,
                        parentColumns = "id",
                        childColumns = "cropId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Supply.class,
                        parentColumns = "id",
                        childColumns = "supplyId",
                        onDelete = ForeignKey.CASCADE
                )})
public class SuppliesUsed {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private long cropId;

    @NonNull
    private int quantity;

    @NonNull
    private long supplyId;

    @Ignore
    public SuppliesUsed(long cropId, long supplyId, int quantity) {
        this(supplyId, quantity);
        this.cropId = cropId;
    }

    public SuppliesUsed(long id, long cropId, long supplyId, int quantity) {
        this(cropId, supplyId, quantity);
        this.id = id;
    }

    @Ignore
    public SuppliesUsed(long supplyId, int quantity) {
        this.supplyId = supplyId;
        this.quantity = quantity;
    }

    public long getId() { return id; }
    public void setId(long i) { if (i > 0) id = i; }
    public long getCropId() { return cropId; }
    public void setCropId(long c) { if (c > 0) cropId = c; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int q) { if (q > 0) quantity = q; }
    public long getSupplyId() { return supplyId; }
    public void setSupplyId(long s) { if (s > 0) supplyId = s; }
}
