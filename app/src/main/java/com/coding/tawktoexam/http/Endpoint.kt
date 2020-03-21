package com.coding.tawktoexam.http

import com.coding.tawktoexam.entity.UserEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface Endpoint {

    @GET("/users")
    fun getUsers(@Query("since") id: String): Observable<List<UserEntity>>

    @GET
    fun getNextData(@Url url: String): Observable<List<UserEntity>>

    @GET("/users/{userName}")
    fun getUserInfo(@Path("userName") userName: String): Observable<UserEntity>
}