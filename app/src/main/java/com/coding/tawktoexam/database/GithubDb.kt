package com.coding.tawktoexam.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.coding.tawktoexam.dao.UserDao
import com.coding.tawktoexam.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class GithubDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: GithubDb? = null

        fun getDatabase(context: Context): GithubDb {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubDb::class.java,
                    "github_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}