package com.hyeok.example.gif.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import com.hyeok.example.gif.model.Gif
import io.reactivex.disposables.CompositeDisposable

class GifViewModel : ViewModel(){

    lateinit var gifList : LiveData<PagedList<Gif>>
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}