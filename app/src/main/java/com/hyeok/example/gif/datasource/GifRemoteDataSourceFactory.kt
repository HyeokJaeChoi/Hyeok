package com.hyeok.example.gif.repository.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import com.hyeok.example.gif.api.GifService
import com.hyeok.example.gif.model.Gif
import io.reactivex.disposables.CompositeDisposable

class GifRemoteDataSourceFactory(private val compositeDisposable: CompositeDisposable, private val gifService: GifService)
    : DataSource.Factory<Int, Gif>() {

    val gifDataSourceLiveData = MutableLiveData<GifRemoteDataSource>()

    override fun create(): DataSource<Int, Gif> {
        val gifRemoteDataSource = GifRemoteDataSource(compositeDisposable, gifService)
        gifDataSourceLiveData.postValue(gifRemoteDataSource)
        return gifRemoteDataSource
    }

}