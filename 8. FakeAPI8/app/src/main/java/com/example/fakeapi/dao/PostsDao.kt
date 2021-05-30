package com.example.fakeapi.dao

import com.example.fakeapi.Post
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class PostsDao {
    fun makePost(realm: Realm, post: Post) {
        realm.beginTransaction()
        realm.insert(PostDb(post))
        realm.commitTransaction()
    }

    fun makePosts(realm: Realm, posts: List<Post>) {
        realm.beginTransaction()
        realm.insert(posts.map { PostDb(it) })
        realm.commitTransaction()

    }

    fun deletePost(realm: Realm, post: Post) {
        realm.beginTransaction()
        realm.where(PostDb::class.java).equalTo("id",post.id).findFirst()?.deleteFromRealm()
        realm.commitTransaction()
    }

    fun deleteAll(realm: Realm) {
        realm.executeTransactionAsync {
            realm.deleteAll()
        }
    }

    fun downloadPosts(realm: Realm): RealmResults<PostDb> {
        return realm.where<PostDb>().findAll()
    }
}