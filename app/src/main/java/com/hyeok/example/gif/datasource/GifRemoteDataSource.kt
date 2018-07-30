package com.hyeok.example.gif.repository.datasource

import android.arch.paging.ItemKeyedDataSource
import android.util.Log
import com.hyeok.example.gif.api.GifService
import com.hyeok.example.gif.model.Gif
import io.reactivex.disposables.CompositeDisposable

class GifRemoteDataSource(private val compositeDisposable: CompositeDisposable, private val gifService: GifService)
    : ItemKeyedDataSource<Int, Gif>(){
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Gif>) {
        compositeDisposable.add(
                gifService.getList()
                        .subscribe({
                            it.response()?.body()?.let{
                                callback.onResult(it)
                            }
                        },{
                            Log.d("GitRemoteDataSource", "Initial Gif fetch failed")
                        }
                 )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Gif>) {
        compositeDisposable.add(
                gifService.getList()
                        .subscribe({
                            it.response()?.body()?.let{
                                callback.onResult(it)
                            }
                        },{
                            Log.d("GitRemoteDataSource", "After Gif fetch failed")
                        }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Gif>) {
        //Do Nothing
    }

    override fun getKey(item: Gif): Int {
        return item.id
    }
}