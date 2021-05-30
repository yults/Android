package com.example.fakeapi.dao

import com.example.fakeapi.Post
import io.realm.RealmObject


open class PostDb(
    var id: Int = 0,
    var title: String = "",
    var body: String = "",
    var userId: Int = 0
) : RealmObject() {
    constructor(post: Post) : this() {
        id = post.id
        title = post.title
        body = post.body
        userId = post.userId
    }
}