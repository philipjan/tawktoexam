package com.coding.tawktoexam.activity

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coding.tawktoexam.R
import com.coding.tawktoexam.databinding.ActivityMainBinding
import com.coding.tawktoexam.entity.UserEntity
import com.coding.tawktoexam.fragment.ProfileFragment
import com.coding.tawktoexam.utility.Utils
import com.coding.tawktoexam.utility.lastItemListener
import com.coding.tawktoexam.viewmodel.AppViewModelFactory
import com.coding.tawktoexam.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity(), SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var vmFactory: AppViewModelFactory

    private lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vmFactory = AppViewModelFactory(application)
        viewModel = ViewModelProvider(this, vmFactory).get(MainActivityViewModel::class.java)
        binder.searchView.setOnQueryTextListener(this)
        binder.searchView.setOnCloseListener(this)
        initializeIndicatorStatus()
        initializeRecyclerView()
        initializeUserLiveData()
    }

    private fun initializeUserLiveData() {
        viewModel.getUsersLiveData().observe(this, Observer {
            userList ->
            viewModel.getAdapter().updateList(userList.toMutableList())
            if (userList.isNullOrEmpty()) {
                viewModel.getUsers()
            }
        })

        viewModel.getSearchedList().observe(this, Observer {
            viewModel.getAdapter().updateList(it.toMutableList())
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

    override fun onClose(): Boolean {
        setDefaultList()
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        LOG(MainActivity::class.java, "onQueryTextSubmit: $query")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        LOG(MainActivity::class.java, "onQueryTextChange: $newText")
        if (newText.isNullOrBlank()) {
           setDefaultList()
        } else {
            viewModel.searchUser(newText)
        }
       return true
    }

    private fun initializeRecyclerView() {
        binder.userRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            this.adapter = viewModel.getAdapter()
        }
        binder.userRecyclerView.lastItemListener {
            viewModel.getUsers()
        }

        viewModel.getAdapter().setClickListener {
            gotoProfile(it)
        }
    }

    private fun setRefreshing(isRefreshing: Boolean) {
        binder.swipeRefreshLayout.isRefreshing = isRefreshing
    }

    private fun gotoProfile(user: UserEntity) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ProfileFragment.getInstance(user.login))
            .addToBackStack("profile")
            .commit()
    }

    private fun setDefaultList() {
        viewModel.getUsersLiveData().let {
            viewModel.getAdapter().updateList(it.value!!.toMutableList())
        }
    }
}
