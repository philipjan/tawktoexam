package com.coding.tawktoexam.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.coding.tawktoexam.database.GithubDb
import com.coding.tawktoexam.http.NetworkModule
import com.coding.tawktoexam.utility.Utils

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val service = NetworkModule(application).getGitHubService()
    val db: GithubDb = GithubDb.getDatabase(application)
    var pref = application.getSharedPreferences(Utils.PREF_NAME, Context.MODE_PRIVATE)

    fun saveLastIndex(index: Int) {
        pref.edit().apply {
            this.putInt(Utils.LAST_INDEX, index)
            this.apply()
        }
    }

    fun getLastIndex(): Int {
        return pref.getInt(Utils.LAST_INDEX, 0)
    }
}