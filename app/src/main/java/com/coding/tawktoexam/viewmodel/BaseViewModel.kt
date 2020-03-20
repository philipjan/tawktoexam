package com.coding.tawktoexam.viewmodel

import androidx.lifecycle.ViewModel
import com.coding.tawktoexam.http.NetworkModule

open class BaseViewModel : ViewModel() {
    val service = NetworkModule.getGitHubService()
}