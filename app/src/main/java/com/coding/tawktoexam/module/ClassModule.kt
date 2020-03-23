package com.coding.tawktoexam.module

import com.coding.tawktoexam.adapter.UserAdapter
import io.reactivex.disposables.CompositeDisposable
import org.koin.dsl.module

val classModule = module {
    single { UserAdapter() }
    factory { CompositeDisposable() }
}