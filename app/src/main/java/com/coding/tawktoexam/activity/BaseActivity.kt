package com.coding.tawktoexam.activity

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun LOG(clazz: Class<*>, msg: String) {
        Log.d(clazz.simpleName, msg)
    }
}