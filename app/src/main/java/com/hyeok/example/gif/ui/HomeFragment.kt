package com.hyeok.example.gif.ui


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.hyeok.example.R
import com.hyeok.example.gif.presenter.GifContract
import com.hyeok.example.gif.presenter.GifPresenter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), GifContract.View {

    lateinit override var presenter : GifPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        DividerItemDecoration(gif_recycler_view.context, StaggeredGridLayoutManager.HORIZONTAL).apply {
            gif_recycler_view.addItemDecoration(this)
            gif_recycler_view.setHasFixedSize(false)
        }
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun setGifList() {

    }

}
