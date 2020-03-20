package com.coding.tawktoexam.http

import com.coding.tawktoexam.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface Endpoint {

    @GET("/users")
    fun getUsers(@Query("since") id: String): Observable<List<User>>

    @GET
    fun getNextData(@Url url: String): Observable<List<User>>
}