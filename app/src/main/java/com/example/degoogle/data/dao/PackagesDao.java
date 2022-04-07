package com.example.degoogle.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.degoogle.data.entities.InstalledPackages;

import java.util.List;

@Dao
public interface PackagesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(InstalledPackages installedPackages);
    @Update
    void update(InstalledPackages installedPackages);
    @Delete
    void delete(InstalledPackages installedPackages);
    @Query("SELECT * FROM InstalledPackages")
    LiveData<List<InstalledPackages>> getAllPackages();
}