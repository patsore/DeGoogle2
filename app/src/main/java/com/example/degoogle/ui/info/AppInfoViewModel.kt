package com.example.degoogle.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.degoogle.data.entities.InstalledPackages
import com.example.degoogle.data.PackagesDao
import kotlinx.coroutines.launch

class AppInfoViewModel(private val packagesDao: PackagesDao) : ViewModel() {
    private fun insertItem(item: InstalledPackages){
        viewModelScope.launch {
            packagesDao.insert(item)
        }

    }
    private fun getNewItem(packageName: String, version: String): InstalledPackages {
        return InstalledPackages(
            appPackage = packageName,
            version = version
        )
    }
    fun addNewItem(packageName: String, version: String){
        val newItem = getNewItem(packageName, version)
        insertItem(newItem)
        }
    fun isEntryValid(packageName: String, version: String): Boolean {
        if (packageName.isBlank() || version.isBlank()) {
            return false
        }
        return true
    }
}
class AppInfoViewModelFactory(private val packagesDao: PackagesDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AppInfoViewModel(packagesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}