package com.coding.tawktoexam.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.coding.tawktoexam.R
import com.coding.tawktoexam.databinding.FragmentProfileBinding
import com.coding.tawktoexam.entity.UserEntity
import com.coding.tawktoexam.utility.Utils
import com.coding.tawktoexam.viewmodel.ProfileBindingViewModel
import com.coding.tawktoexam.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileBindingViewModel: ProfileBindingViewModel
    private var username: String? = null

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
        profileViewModel = getViewModel()
        profileBindingViewModel = ProfileBindingViewModel()
        return profileBinder.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LOG(ProfileFragment::class.java, "Username: ${arguments?.getString("usr", "null")}")
        username = arguments?.getString("usr", "null")
        initializeUIListeners()
        initializeLiveData()
        username?.let {
            setToolbarTitle(it)
            profileViewModel.getUser(it)
        }
    }

    override fun onRefresh() {
        username?.let { profileViewModel.getUser(it) }
    }

    private fun setToolbarTitle(username: String) {
        profileBinder.profileToolbar.title = username
    }

    private fun initializeClickListeners(currentValue: UserEntity) {
        profileBinder.profileSave.setOnClickListener {
            var newValue = UserEntity(
                login = currentValue.login,
                avatarUrl = currentValue.avatarUrl,
                bio = currentValue.bio,
                blog = currentValue.blog,
                company = currentValue.company,
                createdAt = currentValue.createdAt,
                email = currentValue.email,
                eventsUrl = currentValue.eventsUrl,
                followers = currentValue.followers,
                followersUrl = currentValue.followersUrl,
                following = currentValue.following,
                followingUrl = currentValue.followingUrl,
                fullName = currentValue.fullName,
                gistUrl = currentValue.gistUrl,
                gravatarId = currentValue.gravatarId,
                hirable = currentValue.hirable,
                htmlUrl = currentValue.htmlUrl,
                id = currentValue.id,
                isSiteAdmin = currentValue.isSiteAdmin,
                location = currentValue.location,
                nodeId = currentValue.nodeId,
                offLineNote = currentValue.offLineNote,
                organizationsUrl = currentValue.organizationsUrl,
                publicGist = currentValue.publicGist,
                publicRepo = currentValue.publicRepo,
                receivedEventsUrl = currentValue.receivedEventsUrl,
                reposUrl = currentValue.reposUrl,
                starredUrl = currentValue.starredUrl,
                subscriptionsUrl = currentValue.subscriptionsUrl,
                type = currentValue.type,
                updatedAt = currentValue.updatedAt,
                url = currentValue.url
            )
            newValue.apply {
                this.offLineNote = profileBinder.profileNoteInputEditext.text.toString()
            }.apply {
                profileViewModel.updateCurrentProfile(user = this)
            }
        }
    }

    private fun initializeLiveData() {
        profileViewModel.getUserData().observe(viewLifecycleOwner, Observer {
            profileBindingViewModel.bind(it)
            profileBinder.viewModel = profileBindingViewModel
            initializeClickListeners(it)
        })

        profileViewModel.getLoadingStatus().observe(viewLifecycleOwner, Observer {
            when(it) {
                Utils.LOADING -> setRefreshing(true)
                Utils.DONE -> setRefreshing(false)
                Utils.ERROR -> setRefreshing(false)
            }
        })
    }

    private fun setRefreshing(refreshing: Boolean) {
        profileBinder.swipeRefreshLayoutProfile.isRefreshing = refreshing
    }

    private fun initializeUIListeners() {
        profileBinder.swipeRefreshLayoutProfile.setOnRefreshListener(this)
    }
}
