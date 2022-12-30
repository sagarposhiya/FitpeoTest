package com.example.fitpeoassignment.api

import com.example.fitpeoassignment.api.models.Data
import retrofit2.Call
import retrofit2.http.GET

interface DataAPI {

    @GET("photos")
    fun getData(): Call<ArrayList<Data>>
}