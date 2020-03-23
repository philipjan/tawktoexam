package com.coding.tawktoexam.utility

import android.content.Context
import android.content.SharedPreferences
import com.coding.tawktoexam.database.GithubDb

class PersistenceHelper(val context: Context) {

    fun getSharedPrefence(): SharedPreferences {
        return context.getSharedPreferences(Utils.PREF_NAME, Context.MODE_PRIVATE)
    }

    fun getEditor(): SharedPreferences.Editor {
       return getSharedPrefence().edit()
    }

    fun getDb(): GithubDb {
        return GithubDb.getDatabase(context)
    }
}