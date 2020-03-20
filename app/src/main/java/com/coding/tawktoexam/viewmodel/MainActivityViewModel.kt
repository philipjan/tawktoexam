package com.coding.tawktoexam.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainActivityViewModel : ViewModel() {
    val disposable = CompositeDisposable()


    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}