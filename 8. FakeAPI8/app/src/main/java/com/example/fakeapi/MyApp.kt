package com.example.fakeapi

import android.app.Application
import com.example.fakeapi.api.FakeAPIService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit


class MyApp : Application() {

    lateinit var apiService: FakeAPIService
        private set
    lateinit var post: Post

    companion object {
        lateinit var instance: MyApp
            private set
    }

    @ExperimentalSerializationApi
    override fun onCreate() {
        super.onCreate()
        instance = this
        val retrofit = Retrofit.Builder()
            .client(OkHttpClient())
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
        apiService = retrofit.create(FakeAPIService::class.java)
        Realm.init(this)
    }

}