package com.example.farminginventorytracker.model.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "crop_table",
        foreignKeys = {
                @ForeignKey(
                        entity = CropTypes.class,
                        parentColumns = "id",
                        childColumns = "typeId",
                        onDelete = ForeignKey.CASCADE
                )})
public class Crop {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    private long typeId;

    @NonNull
    private Date datePlanted;

    @Ignore
    public Crop(Date datePlanted) {
        this.datePlanted = datePlanted;
    }

    @Ignore
    public Crop(long typeId, Date datePlanted) {
        this(datePlanted);
        this.typeId = typeId;
    }

    public Crop(long id, long typeId, Date datePlanted) {
        this(typeId, datePlanted);
        this.id = id;
    }

    public long getTypeId() { return typeId; }
    public void setTypeId(long t) { if (t > 0) typeId = t; }
    public Date getDatePlanted() { return datePlanted; }
    public long getId() { return id; }
    public void setId(long i) { if (i > 0) id = i; }

    @Override public String toString() { return "{type} planted on " + new SimpleDateFormat("dd/MM/yy").format(getDatePlanted()) + "."; }
}