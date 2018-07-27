package com.hyeok.example.gif.api

import com.hyeok.example.BuildConfig
import com.hyeok.example.gif.model.Gif
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.Result
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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
}