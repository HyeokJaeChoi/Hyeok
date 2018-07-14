package com.hyeok.example.auth.presenter

import android.content.Intent
import android.view.View
import com.hyeok.example.base.presenter.BasePresenter
import com.hyeok.example.base.BaseView

interface AuthContract{

    interface View : BaseView<Presenter> {
        fun startMain(intent : Intent)
    }

    interface Presenter : BasePresenter {
        var authView : View

        fun <T>setCredential(authToken : T)
        fun executeAuthTask(v : android.view.View)
        fun disposeAll()
    }
}