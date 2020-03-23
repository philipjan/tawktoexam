package com.coding.tawktoexam.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.coding.tawktoexam.R
import com.coding.tawktoexam.activity.MainActivity
import com.coding.tawktoexam.databinding.FragmentProfileBinding
import com.coding.tawktoexam.viewmodel.AppViewModelFactory
import com.coding.tawktoexam.viewmodel.ProfileViewModel

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BaseFragment() {

    private lateinit var vmFactory: AppViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    companion object {
        var INSTANCE: ProfileFragment? = null
        fun getInstance(username: String): ProfileFragment {
            if (INSTANCE == null) {
                INSTANCE = ProfileFragment()
            }
            Bundle().apply {
                this.putString("usr", username)
                INSTANCE!!.arguments = this
            }
            return INSTANCE as ProfileFragment
        }
    }

    private lateinit var profileBinder: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        profileBinder = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false )
        vmFactory = AppViewModelFactory((activity as MainActivity).application)
        profileViewModel = ViewModelProvider(this, vmFactory).get(ProfileViewModel::class.java)
        return profileBinder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LOG(ProfileFragment::class.java, "Username: ${arguments?.getString("usr", "null")}")
        val username = arguments?.getString("usr", "null")
        username?.let {
            setToolbarTitle(it)
            profileViewModel.getUser(it)
        }
    }

    private fun setToolbarTitle(username: String) {
        profileBinder.profileToolbar.title = username
    }
}
