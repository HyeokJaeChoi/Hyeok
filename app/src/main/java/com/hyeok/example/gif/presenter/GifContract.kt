package com.hyeok.example.gif.presenter

import com.hyeok.example.base.BaseView
import com.hyeok.example.base.presenter.BasePresenter

interface GifContract{

    interface View : BaseView<GifPresenter>{
        fun setGifList()
    }

    interface Presenter : BasePresenter{
        var gifView : View

        fun loadGif()
    }

}