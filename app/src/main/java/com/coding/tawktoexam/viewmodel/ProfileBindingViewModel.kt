package com.coding.tawktoexam.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.coding.tawktoexam.R
import com.coding.tawktoexam.entity.UserEntity

class ProfileBindingViewModel: ViewModel() {

    private val username = MutableLiveData<String>()
    private val avatarUrl = MutableLiveData<String>()
    private val followerCount = MutableLiveData<String>()
    private val followingCount = MutableLiveData<String>()
    private val fullName = MutableLiveData<String>()
    private val company = MutableLiveData<String>()
    private val blogUrl = MutableLiveData<String>()
    private val offlineNote = MutableLiveData<String>()
    private val noteIcon = MutableLiveData<Int>()


    fun bind(userEntity: UserEntity) {
        username.value = userEntity.login
        avatarUrl.value = userEntity.avatarUrl
        followerCount.value = "Follower: ${userEntity.followers}"
        followingCount.value = "Following: ${userEntity.following}"
        fullName.value = "Name: ${userEntity.fullName}"
        company.value = "Company: ${userEntity.company}"
        blogUrl.value = "Blog: ${userEntity.blog}"
        offlineNote.value = userEntity.offLineNote
        noteIcon.value = if (userEntity.offLineNote.isBlank()) -1 else R.drawable.ic_note
    }

    fun username() = username
    fun avatarUrl() = avatarUrl
    fun followerCount() = followerCount
    fun followingCount() = followingCount
    fun fullName() = fullName
    fun company() = company
    fun blogUrl() = blogUrl
    fun offlineNote() = offlineNote
    fun noteIcon() = noteIcon
}