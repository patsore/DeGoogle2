package com.example.degoogle.data

import androidx.room.*
import com.example.degoogle.data.entities.InstalledPackages
import kotlinx.coroutines.flow.Flow

@Dao
interface PackagesDao {
@Insert(onConflict = OnConflictStrategy.IGNORE)
suspend fun insert(installedPackages: InstalledPackages)
@Update
suspend fun update(installedPackages: InstalledPackages)
@Delete
suspend fun delete(installedPackages: InstalledPackages)
@Query("SELECT * from InstalledPackages WHERE appPackage = :appPackage")
fun getItems(appPackage:String): Flow<InstalledPackages>
}