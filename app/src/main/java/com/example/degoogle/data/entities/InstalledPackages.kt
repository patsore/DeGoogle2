package com.example.degoogle.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InstalledPackages(
    @PrimaryKey
    val appPackage: String,
    @ColumnInfo(name = "app version")
    val version: String){}