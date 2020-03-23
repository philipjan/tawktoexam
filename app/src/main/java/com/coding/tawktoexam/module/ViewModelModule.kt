package com.coding.tawktoexam.module

import com.coding.tawktoexam.viewmodel.MainActivityViewModel
import com.coding.tawktoexam.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainActivityViewModel() }
    viewModel { ProfileViewModel() }
}