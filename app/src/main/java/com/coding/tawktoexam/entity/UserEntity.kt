package com.coding.tawktoexam.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tbl_user")
data class UserEntity(

    @SerializedName("login")
    @ColumnInfo(name = "login_name")
    val login: String,

    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @SerializedName("node_id")
    @ColumnInfo(name = "node_id")
    val nodeId: String,

    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,

    @SerializedName("gravatar_id")
    @ColumnInfo(name = "gravatar_id")
    val gravatarId: String,

    @SerializedName("url")
    @ColumnInfo(name = "url")
    val url: String,

    @SerializedName("html_url")
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,

    @SerializedName("followers_url")
    @ColumnInfo(name = "followers_url")
    val followersUrl: String,

    @SerializedName("following_url")
    @ColumnInfo(name = "following_url")
    val followingUrl: String,

    @SerializedName("gists_url")
    @ColumnInfo(name = "gists_url")
    val gistUrl: String,

    @SerializedName("starred_url")
    @ColumnInfo(name = "starred_url")
    val starredUrl: String,

    @SerializedName("subscriptions_url")
    @ColumnInfo(name = "subscriptions_url")
    val subscriptionsUrl: String,

    @SerializedName("organizations_url")
    @ColumnInfo(name = "organizations_url")
    val organizationsUrl: String,

    @SerializedName("repos_url")
    @ColumnInfo(name = "repos_url")
    val reposUrl: String,

    @SerializedName("events_url")
    @ColumnInfo(name = "events_url")
    val eventsUrl: String,

    @SerializedName("received_events_url")
    @ColumnInfo(name = "received_events_url")
    val receivedEventsUrl: String,

    @SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String,

    @SerializedName("site_admin")
    @ColumnInfo(name = "site_admin")
    val isSiteAdmin: Boolean
)