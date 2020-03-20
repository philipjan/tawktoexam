package com.coding.tawktoexam.viewmodel

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : BaseViewModel() {
    private val disposable = CompositeDisposable()
    private var startIndexId = 0
    private val TAG = "MainActivityViewModel"

    // TODO: 20/03/2020 add some backoff multiplier for Retry
    fun getUsers() {
        disposable.add(
            service.getUsers(startIndexId.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {  }
                .doOnTerminate {  }
                .subscribe(
                    {
                        Log.d(TAG, "$it")
                        startIndexId = it.lastOrNull()?.id ?: 0
                        Log.d(TAG, "Last Index: $startIndexId")
                        // TODO: 20/03/2020 do some database insert stuff
                    },
                    {
                        Log.e(TAG, "Throwable: $it")
                    }
                )
        )
    }

    fun getNextUrl(url: String) {
        disposable.add(
            service.getNextData("https://api.github.com/users?since=46")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {  }
                .doOnTerminate {  }
                .subscribe(
                    {
                        Log.d(TAG, "$it")
                        // TODO: 20/03/2020 do some database insert stuff
                    },
                    {
                        Log.e(TAG, "Throwable: $it")
                    }
                )
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}