package com.coding.tawktoexam.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coding.tawktoexam.entity.UserEntity
import com.coding.tawktoexam.utility.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(app: Application) : BaseViewModel(application = app) {

    private val disposable = CompositeDisposable()
    private val TAG = "ProfileViewModel"

    private val LOADING_INDICATOR = MutableLiveData<Int>()

    private fun getFullUserInfo(username: String) {
        disposable.add(
            service.getUserInfo(username.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { LOADING_INDICATOR.value = Utils.LOADING }
                .doOnTerminate { LOADING_INDICATOR.value = Utils.DONE }
                .subscribe(
                    {
                        Log.d(TAG, "getFullUserInfo: $it")
                        updateCurrentProfile(it)
                    },
                    { throwable ->
                        LOADING_INDICATOR.value = Utils.ERROR
                        Log.e(TAG, "getFullUserInfo: $throwable")
                    }
                )
        )
    }

     fun getUser(username: String) {
        disposable.add(
            db.userDao().searchUserByUserName(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { LOADING_INDICATOR.value = Utils.LOADING }
                .doOnTerminate { LOADING_INDICATOR.value = Utils.DONE }
                .subscribe(
                    {
                        if (it.alreadyHaveFullProfile()) {
                            Log.d(TAG, "getUser ${it.fullName}")
                        } else {
                            getFullUserInfo(username)
                        }
                    },
                    { throwable ->
                        LOADING_INDICATOR.value = Utils.ERROR
                        Log.e(TAG, "getUser: $throwable")
                    }
                )
        )
    }

    private fun updateCurrentProfile(user: UserEntity) {
        disposable.add(
            db.userDao().updateUser(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG, "updateCurrentProfile: SUCCESS")
                    },
                    { throwable ->
                        Log.e(TAG, "updateCurrentProfile: $throwable")
                    }
                )
        )
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}