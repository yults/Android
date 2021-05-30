package com.example.fakeapi

import androidx.room.*

@Dao
interface PostDao {
    @Query("SELECT * FROM post")
    fun getAll()
    @Delete
    fun deletePost(post : Post)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(post: Post)
}