package com.example.degoogle

import android.app.Application
import com.example.degoogle.data.RoomPackagesDatabase

class DegoogleApplication : Application() {
    val database: RoomPackagesDatabase by lazy {RoomPackagesDatabase.getDatabase(this)}
}