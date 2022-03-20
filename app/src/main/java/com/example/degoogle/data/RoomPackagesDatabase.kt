package com.example.degoogle.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.degoogle.data.entities.InstalledPackages

@Database(entities = [InstalledPackages::class], version = 1, exportSchema = false)
public abstract class RoomPackagesDatabase : RoomDatabase() {

    abstract fun packagesDao(): PackagesDao

        companion object {
            @Volatile
            private var INSTANCE: RoomPackagesDatabase? = null

            fun getDatabase(context: Context): RoomPackagesDatabase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomPackagesDatabase::class.java,
                        "item_database"
                    )
                        // Wipes and rebuilds instead of migrating if no Migration object.
                        // Migration is not part of this codelab.
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    // return instance
                   return instance
                }
            }


        }
}