package com.coding.tawktoexam.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.coding.tawktoexam.database.GithubDb
import com.coding.tawktoexam.http.NetworkModule

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val service = NetworkModule.getGitHubService()
    val db: GithubDb = GithubDb.getDatabase(application)
}