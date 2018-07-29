package com.hyeok.example.gif.api

import com.hyeok.example.BuildConfig
import com.hyeok.example.gif.model.Gif
import com.hyeok.example.gif.model.GifResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.Result
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface GifService{

    companion object {
        private const val BASE_URL = BuildConfig.serverUrl

        fun create() : GifService{
            return Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(OkHttpClient.Builder()
                            .addInterceptor(HttpLoggingInterceptor()
                                    .setLevel(HttpLoggingInterceptor.Level.BODY))
                            .build())
                    .baseUrl(BASE_URL)
                    .build()
                    .create(GifService::class.java)
        }
    }

    @GET("/list")
    fun getList() : Single<Result<List<Gif>>>
    @DELETE("/delete:" + "{id}")
    fun deleteGif(@Path("id") id : Int) : Single<Result<String>>
    @Multipart
    @POST("/upload")
    fun uploadGif(@Part("img") img : MultipartBody.Part, @Part("tag") tag : RequestBody) : Single<Result<GifResponse>>

}