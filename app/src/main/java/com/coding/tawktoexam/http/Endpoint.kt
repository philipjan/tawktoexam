package com.coding.tawktoexam.http

import com.coding.tawktoexam.entity.UserEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoint {

    @GET("/users")
    @Headers("Authorization: Bearer b738520b7555b698ed52ea7d5fcf661b697b0213")
    fun getUsers(@Query("since") id: String): Observable<List<UserEntity>>

    @GET("/users/{userName}")
    @Headers("Authorization: Bearer b738520b7555b698ed52ea7d5fcf661b697b0213")
    fun getUserInfo(@Path("userName") userName: String): Observable<UserEntity>
}