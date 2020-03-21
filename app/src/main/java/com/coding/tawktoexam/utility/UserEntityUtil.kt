package com.coding.tawktoexam.utility

import androidx.recyclerview.widget.DiffUtil
import com.coding.tawktoexam.entity.UserEntity

class UserEntityUtil : DiffUtil.ItemCallback<UserEntity>() {
    override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
        return oldItem.id == newItem.id ||
                oldItem.fullName == newItem.fullName ||
                oldItem.company == newItem.company ||
                oldItem.blog == newItem.blog ||
                oldItem.location == newItem.location ||
                oldItem.email == newItem.email ||
                oldItem.hirable == newItem.hirable ||
                oldItem.bio == newItem.bio ||
                oldItem.publicRepo == newItem.publicRepo ||
                oldItem.publicGist == newItem.publicGist ||
                oldItem.followers == newItem.followers ||
                oldItem.following == newItem.following ||
                oldItem.createdAt == newItem.createdAt ||
                oldItem.updatedAt == newItem.updatedAt
    }
}