package com.coding.tawktoexam.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.coding.tawktoexam.R
import com.coding.tawktoexam.databinding.ActivityMainBinding
import com.coding.tawktoexam.viewmodel.AppViewModelFactory
import com.coding.tawktoexam.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var vmFactory: AppViewModelFactory

    private lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vmFactory = AppViewModelFactory(application)
        viewModel = ViewModelProvider(this, vmFactory).get(MainActivityViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUsers()
    }
}
