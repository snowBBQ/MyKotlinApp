package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("apps")
    fun getOpenApps(): Call<AppResponse>

    @POST("open/{app_name}")
    fun openApp(@Path("app_name") appName: String): Call<Void>

    @POST("close/{app_name}")
    fun closeApp(@Path("app_name") appName: String): Call<Void>
}