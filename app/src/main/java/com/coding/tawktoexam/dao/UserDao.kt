package com.coding.tawktoexam.dao

import androidx.room.*
import com.coding.tawktoexam.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * https://medium.com/androiddevelopers/room-rxjava-acb0cd4f3757
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<UserEntity>): Completable

    @Query("SELECT * FROM tbl_user")
    fun getAllUser(): Flowable<List<UserEntity>>

    @Query("SELECT * FROM tbl_user WHERE tbl_user.login_name LIKE :searchString")
    fun searchUsers(searchString: String): Flowable<List<UserEntity>>

    @Query("SELECT * FROM tbl_user WHERE tbl_user.id = :id")
    fun searchUser(id: Int): Flowable<UserEntity>

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateUser(user: UserEntity): Completable

    @Delete
    fun deleteUser(user: UserEntity): Completable
}