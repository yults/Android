package com.example.fakeapi.api

import com.example.fakeapi.Post
import retrofit2.Call
import retrofit2.http.*

interface FakeAPIService {
    @GET("posts")
    fun downloadPosts():Call<List<Post>>

    @POST("posts")
    fun makePost(@Body post: Post):Call<Post>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id:Int?):Call<Unit>
}