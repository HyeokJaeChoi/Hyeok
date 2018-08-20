package com.hyeok.example.gif.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hyeok.example.R
import com.hyeok.example.gif.adapter.GifAdapter
import com.hyeok.example.gif.api.GifService
import com.hyeok.example.gif.model.Gif
import com.hyeok.example.gif.presenter.GifContract
import com.hyeok.example.gif.presenter.GifPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), GifContract.View {

    lateinit override var presenter : GifPresenter
    private lateinit var gifViewModel: GifViewModel
    private lateinit var gifListAdapter : GifAdapter

    val gifService = GifService.create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        DividerItemDecoration(gif_recycler_view.context, StaggeredGridLayoutManager.HORIZONTAL).apply {
            gif_recycler_view.addItemDecoration(this)
            gif_recycler_view.setHasFixedSize(false)
        }

        gifListAdapter = GifAdapter {
            gifService.clickGif(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        (gif_recycler_view.adapter as GifAdapter).currentList?.dataSource?.invalidate()
                    },{

                    })
        }
        gif_recycler_view.adapter = gifListAdapter
        gifViewModel = ViewModelProviders.of(this).get(GifViewModel::class.java)
        gifViewModel.gifLiveDataList.observe(this, Observer<PagedList<Gif>> { gifListAdapter.submitList(it) })
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun setGifList() {
    }

}
