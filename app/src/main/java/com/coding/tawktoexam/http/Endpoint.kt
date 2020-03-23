package com.coding.tawktoexam.http

import com.coding.tawktoexam.entity.UserEntity
import com.coding.tawktoexam.utility.Utils
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {

    @GET("/users")
    @Headers("Authorization: Bearer ${Utils.KY}")
    fun getUsers(@Query("since") id: String): Observable<List<UserEntity>>

    @GET("/users/{userName}")
    @Headers("Authorization: Bearer ${Utils.KY}")
    fun getUserInfo(@Path("userName") userName: String): Observable<UserEntity>
}