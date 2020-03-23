package com.coding.tawktoexam.activity

import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.coding.tawktoexam.R
import com.coding.tawktoexam.databinding.ActivityMainBinding
import com.coding.tawktoexam.entity.UserEntity
import com.coding.tawktoexam.fragment.ProfileFragment
import com.coding.tawktoexam.utility.Utils
import com.coding.tawktoexam.utility.lastItemListener
import com.coding.tawktoexam.viewmodel.MainActivityViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity(), SearchView.OnQueryTextListener, SearchView.OnCloseListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binder: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = getViewModel()
        binder.searchView.setOnQueryTextListener(this)
        binder.searchView.setOnCloseListener(this)
        binder.swipeRefreshLayout.setOnRefreshListener(this)
        initializeIndicatorStatus()
        initializeRecyclerView()
        initializeUserLiveData()
    }

    private fun initializeUserLiveData() {
        viewModel.getUsersLiveData().observe(this, Observer {
            userList ->
            viewModel.getUserAdapter().updateList(userList.toMutableList())
            if (userList.isNullOrEmpty()) {
                viewModel.getUsers()
            }
        })

        viewModel.getSearchedList().observe(this, Observer {
            viewModel.getUserAdapter().updateList(it.toMutableList())
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
        if (query.isNullOrBlank()) {
            setDefaultList()
        } else {
            viewModel.searchUser(query)
        }
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

    override fun onRefresh() {
        viewModel.getUsers()
    }

    private fun initializeRecyclerView() {
        binder.userRecyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            this.adapter = viewModel.getUserAdapter()
        }
        binder.userRecyclerView.lastItemListener {
            viewModel.getUsers()
        }

        viewModel.getUserAdapter().setClickListener {
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
            viewModel.getUserAdapter().updateList(it.value!!.toMutableList())
        }
    }
}
