package com.example.fakeapi.api

import com.example.fakeapi.Post
import retrofit2.Response
import retrofit2.http.*

interface FakeAPIService {
    @GET("posts")
   suspend fun downloadPosts():Response<List<Post>>

    @POST("posts")
   suspend fun makePost(@Body post: Post):Response<Post>

    @DELETE("posts/{id}")
   suspend fun deletePost(@Path("id") id:Int?):Response<Unit>
}