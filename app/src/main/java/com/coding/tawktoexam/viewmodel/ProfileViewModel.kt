package com.coding.tawktoexam.viewmodel

import android.app.Application
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(app: Application) : BaseViewModel(application = app) {
    private val disposable = CompositeDisposable()
    private val TAG = "ProfileViewModel"

    fun getUser(username: String?) {
        disposable.add(
            service.getUserInfo(username.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG, "User: $it")
                    },
                    { throwable ->

                    }
                )
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}