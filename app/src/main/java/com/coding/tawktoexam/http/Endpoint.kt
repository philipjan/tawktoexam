package com.coding.tawktoexam.http

import com.coding.tawktoexam.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Endpoint {

    @GET("/users")
    fun getUsers(@Query("since") id: String): Observable<List<User>>
}