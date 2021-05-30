package com.example.fakeapi.dao

import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where

class PostsDao {
    fun makePost(realm: Realm, post: PostDb) {
        realm.executeTransaction {
            realm.insert(post)
        }
    }

    fun makePosts(realm: Realm, postsDB: List<PostDb>) {
        realm.executeTransaction {
            realm.insert(postsDB)
        }
    }

    fun deletePost(realm: Realm, post: RealmResults<PostDb>) {
        realm.executeTransaction {
            post.deleteAllFromRealm()
        }
    }

    fun getPost(realm: Realm, postId: Int): RealmResults<PostDb>? {
        return realm.where<PostDb>().equalTo("id", postId).findAll()
    }

    fun deleteAll(realm: Realm) {
        realm.executeTransaction {
            realm.deleteAll()
        }
    }

    fun downloadPosts(realm: Realm): RealmResults<PostDb> {
        return realm.where<PostDb>().findAll()
    }
}