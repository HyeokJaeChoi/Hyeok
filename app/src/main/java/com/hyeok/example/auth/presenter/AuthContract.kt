package com.hyeok.example.auth.presenter

import android.content.Intent
import com.hyeok.example.base.presenter.BasePresenter
import com.hyeok.example.base.BaseView

interface AuthContract{

    interface View : BaseView<Presenter> {
        fun startMain(intent : Intent)
    }

    interface Presenter : BasePresenter {
        var authView : View

        fun setCredential(buttonId : String)
    }
}