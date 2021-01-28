package com.example.fakeapi

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MyApp : Application() {
    private lateinit var retrofit : Retrofit
    lateinit var apiService : FakeAPIService
    var posts : List<Post> = listOf()
    override fun onCreate() {
        super.onCreate()
        instance = this
        retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        apiService = retrofit.create(FakeAPIService::class.java)
    }
    companion object {
        lateinit var instance: MyApp
            private set
    }
}