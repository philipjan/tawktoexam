package com.coding.tawktoexam

import android.app.Application
import com.coding.tawktoexam.module.classModule
import com.coding.tawktoexam.module.networkKoinModule
import com.coding.tawktoexam.module.prefModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TawkApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TawkApp)
            modules(listOf(
                prefModule,
                networkKoinModule,
                classModule
            ))
        }
    }
}