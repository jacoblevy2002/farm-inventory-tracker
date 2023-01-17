package com.example.farminginventorytracker.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.farminginventorytracker.dao.CropDao;
import com.example.farminginventorytracker.dao.CropTypesDao;
import com.example.farminginventorytracker.dao.SuppliesUsedDao;
import com.example.farminginventorytracker.dao.SupplyDao;
import com.example.farminginventorytracker.dao.ToolDao;
import com.example.farminginventorytracker.model.entities.Converters;
import com.example.farminginventorytracker.model.entities.Crop;
import com.example.farminginventorytracker.model.entities.CropTypes;
import com.example.farminginventorytracker.model.entities.SuppliesUsed;
import com.example.farminginventorytracker.model.entities.Supply;
import com.example.farminginventorytracker.model.entities.Tool;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Crop.class, Supply.class, Tool.class, CropTypes.class, SuppliesUsed.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class FarmDatabase extends RoomDatabase {

    public abstract CropDao cropDao();
    public abstract SupplyDao supplyDao();
    public abstract ToolDao toolDao();
    public abstract CropTypesDao cropTypesDao();
    public abstract SuppliesUsedDao suppliesUsedDao();

    private static FarmDatabase instance;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static FarmDatabase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (FarmDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            FarmDatabase.class, "farm_database")
                            .addCallback(RoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback RoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(instance).execute();
        }
   };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final CropTypesDao ctDao;
        private final CropDao cDao;
        private final SupplyDao sDao;
        private final SuppliesUsedDao suDao;
        private final ToolDao tDao;

        private static CropTypes [] cropTypes = {
                new CropTypes(1, "Tomato"),
                new CropTypes(2, "Potato"),
                new CropTypes(3, "Carrot"),
                new CropTypes(4, "Wheat"),
                new CropTypes(5, "Corn"),
                new CropTypes(6, "Beetroot"),
                new CropTypes(7, "Apple"),
                new CropTypes(8, "Orange"),
                new CropTypes(9, "Grape"),
                new CropTypes(10, "Cherry"),
        };

        private static Crop [] crops = {
                new Crop(1, 1, new Date()),
                new Crop(2, 2, new Date()),
                new Crop(3, 3, new Date()),
                new Crop(4, 4, new Date()),
                new Crop(5, 5, new Date()),
                new Crop(6, 6, new Date()),
                new Crop(7, 7, new Date()),
                new Crop(8, 8, new Date()),
                new Crop(9, 9, new Date()),
                new Crop(10, 10, new Date()),
        };

        private static Supply [] supplies = {
                new Supply(1, "Fertilizer", 100),
                new Supply(2, "Insecticide", 50),
                new Supply(3, "Antiseptic Spray", 15),
                new Supply(4, "Bedding Freshener", 3),
        };

        private static SuppliesUsed [] suppliesUsed = {
                new SuppliesUsed(1, 1, 1, 3),
                new SuppliesUsed(2, 2, 1, 5),
                new SuppliesUsed(3, 3, 1, 1),
                new SuppliesUsed(4, 4, 1, 6),
                new SuppliesUsed(5, 5, 1, 1),
                new SuppliesUsed(6, 6, 1, 3),
                new SuppliesUsed(7, 7, 1, 6),
                new SuppliesUsed(8, 8, 1, 2),
                new SuppliesUsed(9, 9, 1, 8),
                new SuppliesUsed(10, 10, 1, 10),
        };

        private static Tool[] tools = {
                new Tool("Hoe", 40, 1),
                new Tool("Snow Shovel", 35, 2),
                new Tool("Dirt Shovel", 40, 1),
        };

        PopulateDbAsync(FarmDatabase db) {
            ctDao = db.cropTypesDao();
            sDao = db.supplyDao();
            suDao = db.suppliesUsedDao();
            cDao = db.cropDao();
            tDao = db.toolDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            if (ctDao.getAny().length < 1) for (CropTypes ct : cropTypes) ctDao.insert(ct);
            if (cDao.getAny().length < 1) for (Crop c : crops) cDao.insert(c);
            if (sDao.getAny().length < 1) for (Supply s : supplies) sDao.insert(s);
            if (suDao.getAny().length < 1) for (SuppliesUsed su : suppliesUsed) suDao.insert(su);
            if (tDao.getAny().length < 1) for (Tool t : tools) tDao.insert(t);

            return null;
        }
    }
}
