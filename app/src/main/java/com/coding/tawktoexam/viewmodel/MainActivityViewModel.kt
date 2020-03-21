package com.coding.tawktoexam.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coding.tawktoexam.entity.UserEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private var startIndexId = 0
    private val TAG = "MainActivityViewModel"

    private val usersLiveData = MutableLiveData<List<UserEntity>>()

    init {
        getUserListDb()
    }

    fun getUsersLiveData() = usersLiveData

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
                        // get the last index
                        startIndexId = it.lastOrNull()?.id ?: 0
                        insertData(it)
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

    private fun insertData(users: List<UserEntity>) {
        disposable.add(
            db.userDao().insert(users)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG, "INSERT COMPLETE!")
                    },
                    { error ->
                        Log.e(TAG, "ERROR WHILE INSERTING DATA! $error")
                    }
                )
        )
    }

    private fun getUserListDb() {
        disposable.add(
            db.userDao().getAllUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { userList ->
                      Log.d(TAG, "DB USER SIZE: ${userList.size}")
                        usersLiveData.value = userList
                    },
                    { error ->
                        Log.e(TAG, "getUserListDb -> $error")
                    }
                )
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}