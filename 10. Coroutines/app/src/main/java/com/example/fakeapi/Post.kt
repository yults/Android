package com.example.fakeapi

import android.os.Parcelable
import com.example.fakeapi.dao.PostDb
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Post(
    var id: Int = -1,
    var title: String="",
    var body: String="",
    var userId: Int = -1
):Parcelable{
    constructor(postDB: PostDb):this(){
        userId= postDB.userId
        id= postDB.id
        title= postDB.title
        body= postDB.body
    }
}

