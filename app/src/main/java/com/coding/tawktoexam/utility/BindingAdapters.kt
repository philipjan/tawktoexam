package com.coding.tawktoexam.utility

import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.coding.tawktoexam.R
import com.squareup.picasso.Picasso

@BindingAdapter("profile")
fun avatarProfile(view: ImageView, avatarUrl: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && avatarUrl != null) {
        avatarUrl.observe(parentActivity, Observer {
            Picasso.get()
                .load(it)
                .error(R.drawable.ic_user_head)
                .into(view)
        })
    }
}


@BindingAdapter("profileResized")
fun avatarProfileResized(view: ImageView, avatarUrl: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && avatarUrl != null) {
        avatarUrl.observe(parentActivity, Observer {
            Picasso.get()
                .load(it)
                .resize(100, 100)
                .centerInside()
                .error(R.drawable.ic_user_head)
                .into(view)
        })
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, value: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && value != null) {
        value.observe(parentActivity, Observer {
            view.text = it
        })
    }
}