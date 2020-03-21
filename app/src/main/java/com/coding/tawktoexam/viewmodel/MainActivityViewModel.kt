package com.coding.tawktoexam.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coding.tawktoexam.adapter.UserAdapter
import com.coding.tawktoexam.entity.Note
import com.coding.tawktoexam.entity.UserEntity
import com.coding.tawktoexam.utility.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    private val disposable = CompositeDisposable()
    private var startIndexId = 0
    private val TAG = "MainActivityViewModel"
    private val adapter = UserAdapter()

    private val usersLiveData = MutableLiveData<List<UserEntity>>()
    private val loadingIndicatorLiveData = MutableLiveData<Int>()

    init {
        getUserListDb()
    }

    fun getAdapter() = adapter
    fun getUsersLiveData() = usersLiveData
    fun getIndicatorStatus() = loadingIndicatorLiveData

    // TODO: 20/03/2020 add some backoff multiplier for Retry
    fun getUsers() {
        Log.d(TAG, "CURRENT INDEX: ${getLastIndex()}")
        disposable.add(
            service.getUsers(getLastIndex().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loadingIndicatorLiveData.value = Utils.LOADING }
                .doOnTerminate { loadingIndicatorLiveData.value = Utils.DONE }
                .subscribe(
                    {
                        Log.d(TAG, "$it")
                        saveLastIndex(it.last().id)
                        Log.d(TAG, "New Index: ${it.last().id}")
                        insertData(it)
                    },
                    {
                        loadingIndicatorLiveData.value = Utils.ERROR
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

    // TODO: 21/03/2020 testing function if can add NOTE in DB
    fun addSampleNote() {
        disposable.add(
            db.noteDao().insertNote(Note(1, "Sample Note 123"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG, "addSampleNote DONE")
                    },
                    { error ->
                        Log.e(TAG, "addSampleNote -> $error")
                    }
                )
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}