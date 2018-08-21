package com.hyeok.example.gif.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.hyeok.example.gif.api.GifService
import com.hyeok.example.gif.model.Gif
import com.hyeok.example.gif.datasource.GifRemoteDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class GifViewModel : ViewModel(){

    var gifLiveDataList : LiveData<PagedList<Gif>>
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val pagedSize = 20
    private val gifDataSourceFactory : GifRemoteDataSourceFactory
    private val gifListConfig : PagedList.Config

    init {
        gifDataSourceFactory = GifRemoteDataSourceFactory(compositeDisposable, GifService.create())
        gifListConfig = PagedList.Config.Builder()
                .setPageSize(pagedSize)
                .setEnablePlaceholders(false)
                .setPrefetchDistance(5)
                .setInitialLoadSizeHint(pagedSize * 2)
                .build()
        gifLiveDataList = LivePagedListBuilder<Int, Gif>(gifDataSourceFactory, gifListConfig).build()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}