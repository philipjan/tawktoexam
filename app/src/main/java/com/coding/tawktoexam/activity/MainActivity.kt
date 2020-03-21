package com.coding.tawktoexam.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coding.tawktoexam.R
import com.coding.tawktoexam.databinding.ActivityMainBinding
import com.coding.tawktoexam.utility.Utils
import com.coding.tawktoexam.utility.lastItemListener
import com.coding.tawktoexam.viewmodel.AppViewModelFactory
import com.coding.tawktoexam.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var vmFactory: AppViewModelFactory

    private lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vmFactory = AppViewModelFactory(application)
        viewModel = ViewModelProvider(this, vmFactory).get(MainActivityViewModel::class.java)
        initializeIndicatorStatus()
        initializeRecyclerView()
        initializeUserLiveData()
    }

    private fun initializeUserLiveData() {
        viewModel.getUsersLiveData().observe(this, Observer {
            userList ->
            showToast("Size: ${userList.size}")
            viewModel.getAdapter().updateList(userList.toMutableList())
            viewModel.addSampleNote()
            if (userList.isNullOrEmpty()) {
                showToast("Getting Users")
                viewModel.getUsers()
            }
        })
    }

    private fun initializeIndicatorStatus() {
        viewModel.getIndicatorStatus().observe(this, Observer {
            when(it) {
                Utils.LOADING -> {
                    setRefreshing(true)
                }
                Utils.DONE -> {
                    setRefreshing(false)
                }
                Utils.ERROR -> {
                    setRefreshing(false)
                }
            }
        })
    }

    private fun initializeRecyclerView() {
        binder.userRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            this.adapter = viewModel.getAdapter()
        }
        binder.userRecyclerView.lastItemListener {
            LOG(MainActivity::class.java, "Last Item Triggered!")
            viewModel.getUsers()
        }

        viewModel.getAdapter().setClickListener {
            showToast("Selected: ${it.login}")
        }
    }

    private fun setRefreshing(isRefreshing: Boolean) {
        binder.swipeRefreshLayout.isRefreshing = isRefreshing
    }
}
