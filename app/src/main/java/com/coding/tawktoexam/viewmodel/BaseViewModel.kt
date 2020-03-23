package com.coding.tawktoexam.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.coding.tawktoexam.http.NetworkModule
import com.coding.tawktoexam.utility.PersistenceHelper
import com.coding.tawktoexam.utility.Utils
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseViewModel(application: Application) : AndroidViewModel(application), KoinComponent {
    val service: NetworkModule by inject()
    val persistenceHelper: PersistenceHelper by inject()

    fun saveLastIndex(index: Int) {
        persistenceHelper.getEditor().apply {
            this.putInt(Utils.LAST_INDEX, index)
            this.apply()
        }
    }

    fun getLastIndex(): Int {
        return persistenceHelper.getSharedPrefence().getInt(Utils.LAST_INDEX, 0)
    }
}