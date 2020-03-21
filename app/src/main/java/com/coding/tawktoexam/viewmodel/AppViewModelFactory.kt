package com.coding.tawktoexam.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AppViewModelFactory (val app: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
           return MainActivityViewModel(app) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}