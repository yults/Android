package com.example.networking

data class Image(
    val id : Int,
    val author: String,
    val width : Int,
    val height : Int,
    val url : String,
    val download_url : String)