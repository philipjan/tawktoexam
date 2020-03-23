package com.coding.tawktoexam.module

import com.coding.tawktoexam.utility.PersistenceHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val persistenceModule = module {
    single { PersistenceHelper(androidContext()) }
}