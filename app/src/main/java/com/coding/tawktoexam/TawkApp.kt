package com.coding.tawktoexam

import android.app.Application
import com.coding.tawktoexam.module.classModule
import com.coding.tawktoexam.module.networkKoinModule
import com.coding.tawktoexam.module.persistenceModule
import com.coding.tawktoexam.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class TawkApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TawkApp)
            androidLogger(Level.INFO)
            modules(listOf(
                persistenceModule,
                networkKoinModule,
                classModule,
                viewModelModule
            ))
        }
    }
}