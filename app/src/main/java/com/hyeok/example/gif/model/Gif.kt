package com.hyeok.example.gif.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Gif(

    @SerializedName("id")
    val id : Int,
    @SerializedName("fileUrl")
    val fileUrl : String,
    @SerializedName("author")
    val author : String,
    @SerializedName("tag")
    val tag : String,
    @SerializedName("view")
    val views : Int,
    @SerializedName("authorId")
    val authorId : Int,
    @SerializedName("createdAt")
    val createdAt : Date,
    @SerializedName("updatedAt")
    val updatedAt : Date

)