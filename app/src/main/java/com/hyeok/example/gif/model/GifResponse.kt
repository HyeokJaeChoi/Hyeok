package com.hyeok.example.gif.model

import com.google.gson.annotations.SerializedName

data class GifResponse(

        @SerializedName("tag")
        val tag : String,
        @SerializedName("author")
        val author : String,
        @SerializedName("authorId")
        val authorId : Int,
        @SerializedName("filename")
        val fileName : String,
        @SerializedName("url")
        val url : String

)