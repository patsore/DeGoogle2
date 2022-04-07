package com.example.degoogle.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.degoogle.data.dao.PackagesDao;
import com.example.degoogle.data.entities.InstalledPackages;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {InstalledPackages.class}, version = 1, exportSchema = false)
public abstract class RoomPackagesDatabase extends RoomDatabase {

    public abstract PackagesDao packagesDao();

    private static volatile RoomPackagesDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoomPackagesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomPackagesDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomPackagesDatabase.class, "packages_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}