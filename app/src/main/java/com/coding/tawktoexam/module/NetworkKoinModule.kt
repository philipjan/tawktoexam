package com.coding.tawktoexam.module

import com.coding.tawktoexam.http.NetworkModule
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val networkKoinModule = module {
    single { NetworkModule(androidApplication()) }
}