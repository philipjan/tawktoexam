package com.coding.tawktoexam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coding.tawktoexam.R
import com.coding.tawktoexam.entity.UserEntity
import com.coding.tawktoexam.utility.UserEntityUtil
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter: ListAdapter<UserEntity, UserAdapter.UserViewHolder>(UserEntityUtil()) {

    private var userList = mutableListOf<UserEntity>()

    inner class UserViewHolder(val view: View): RecyclerView.ViewHolder(view.rootView) {
        val profileImage = view.profileImage
        val userName = view.userName
        val userNote = view.userNote

        fun bind(user: UserEntity) {
            Picasso.get().load(user.avatarUrl)
                .error(R.drawable.ic_user_head)
                .resize(50, 50)
                .into(profileImage)
            userName.text = user.login
            userNote.text = user.offLineNote
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun updateList(items: MutableList<UserEntity>) {
        this.userList.clear()
        this.userList.addAll(items)
        submitList(this.userList)
        notifyDataSetChanged()
    }
}