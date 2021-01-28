@file:Suppress("AndroidUnresolvedRoomSqlReference")

package com.example.fakeapi

import androidx.room.*

@Dao
interface PostsDao {
    @Query("SELECT * FROM post")
    fun getAll() : List<Post>
    @Delete
    fun deletePost(post: Post)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(post: Post)
}