package com.example.recipeapp.Api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    const val API_KEY = "8940adef8c014f048a429b942bd6bf08"
    private val client = OkHttpClient.Builder()
        .build()

    val api : ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}