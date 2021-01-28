package com.example.fakeapi

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Post(
    @PrimaryKey var id : Int?,
    @ColumnInfo(name =  "title")  var title : String?,
    @ColumnInfo(name = "body") var body : String?,
    @ColumnInfo(name = "userId") var userId : Int?)