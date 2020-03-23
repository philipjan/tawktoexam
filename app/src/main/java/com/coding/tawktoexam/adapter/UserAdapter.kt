package com.coding.tawktoexam.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coding.tawktoexam.R
import com.coding.tawktoexam.databinding.ItemUserBinding
import com.coding.tawktoexam.entity.UserEntity
import com.coding.tawktoexam.utility.UserEntityUtil
import com.coding.tawktoexam.viewmodel.ProfileBindingViewModel

class UserAdapter: ListAdapter<UserEntity, UserAdapter.UserViewHolder>(UserEntityUtil()) {

    private var userList = mutableListOf<UserEntity>()
    private var clickListener = { selected: UserEntity -> Unit }

    inner class UserViewHolder(val view: ItemUserBinding): RecyclerView.ViewHolder(view.root), View.OnClickListener {

        val binderViewModel = ProfileBindingViewModel()

        fun bind(user: UserEntity) {
            view.root.setOnClickListener(this)
            binderViewModel.bind(user)
            view.viewModel = binderViewModel
        }

        override fun onClick(v: View?) {
            clickListener.invoke(userList[layoutPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setClickListener(listener: (UserEntity) -> Unit) {
        this.clickListener = listener
    }

    fun updateList(items: MutableList<UserEntity>) {
        this.userList.clear()
        this.userList.addAll(items)
        submitList(this.userList)
        notifyDataSetChanged()
    }
}