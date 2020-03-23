package com.coding.tawktoexam.viewmodel

import androidx.lifecycle.ViewModel
import com.coding.tawktoexam.http.NetworkModule
import com.coding.tawktoexam.utility.PersistenceHelper
import com.coding.tawktoexam.utility.Utils
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseViewModel : ViewModel(), KoinComponent {
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