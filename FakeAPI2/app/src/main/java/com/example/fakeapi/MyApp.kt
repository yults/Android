package com.example.fakeapi

import android.app.Application
import androidx.room.Room
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MyApp : Application() {
    private lateinit var retrofit : Retrofit
    lateinit var apiService : FakeAPIService
    var post : Post? = null
    private var base : AppDatabase? = null
    var dao : PostsDao? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        apiService = retrofit.create(FakeAPIService::class.java)
        base = Room.databaseBuilder(this, AppDatabase::class.java, "database").allowMainThreadQueries().build()
        dao = base?.postDao()
    }
    companion object {
        lateinit var instance: MyApp
            private set
    }
    fun clearData() {
        val posts = dao?.getAll()
        if (posts != null) {
            for (post in posts) {
                dao?.deletePost(post)
            }
        }
    }

}