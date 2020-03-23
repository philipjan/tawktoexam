package com.coding.tawktoexam.fragment

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    fun showToast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
    }

    fun LOG(clazz: Class<*>, msg: String) {
        Log.d(clazz.simpleName, msg)
    }
}