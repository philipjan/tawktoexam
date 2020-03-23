package com.coding.tawktoexam.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coding.tawktoexam.adapter.UserAdapter
import com.coding.tawktoexam.entity.UserEntity
import com.coding.tawktoexam.utility.Utils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.get

class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    private val TAG = "MainActivityViewModel"

    private val disposable: CompositeDisposable = get()
    private val adapter: UserAdapter = get()

    private val usersLiveData = MutableLiveData<List<UserEntity>>()
    private val searchUserLiveData = MutableLiveData<List<UserEntity>>()
    private val loadingIndicatorLiveData = MutableLiveData<Int>()

    init {
        getUserListDb()
    }

    fun getUserAdapter() = adapter
    fun getUsersLiveData() = usersLiveData
    fun getIndicatorStatus() = loadingIndicatorLiveData
    fun getSearchedList() = searchUserLiveData

    // TODO: 20/03/2020 add some backoff multiplier for Retry
    fun getUsers() {
        Log.d(TAG, "CURRENT INDEX: ${getLastIndex()}")
        disposable.add(
            service.getGitHubService().getUsers(getLastIndex().toString())
                .flatMap {
                    Observable.fromIterable(it)
                }
                .flatMap {
                    getFullUserInfo(it)
                }
                .toList()
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
                        Log.e(TAG, "Throwable: getUsers $it")
                    }
                )
        )
    }

    private fun getFullUserInfo(username: UserEntity): Observable<UserEntity> {
        return  service.getGitHubService().getUserInfo(username.login)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it
            }
    }

    private fun insertData(users: List<UserEntity>) {
        disposable.add(
            persistenceHelper.getDb().userDao().insert(users)
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
            persistenceHelper.getDb().userDao().getAllUser()
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

     fun searchUser(value: String) {
         Log.d(TAG, "searchUser VALUE: $value")
         disposable.add(
             persistenceHelper.getDb().userDao().getAllUser()
                 .map { userList ->
                     userList.filter {
                         it.login.contains(value, true) || it.offLineNote.contains(value, true)
                     }
                 }
                 .subscribeOn(Schedulers.computation())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(
                     {
                         searchUserLiveData.value = it
                     },
                     { throwable ->
                         Log.e(TAG, "searchUser: $throwable")
                     }
                 )
         )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}