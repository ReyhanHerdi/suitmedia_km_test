package com.example.suitmediakmtest.data.api

import com.example.suitmediakmtest.data.response.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/users")
    fun getData(
        @Query("page") page: String,
        @Query("per_page") per_page: String
    ): Call<Response>
}