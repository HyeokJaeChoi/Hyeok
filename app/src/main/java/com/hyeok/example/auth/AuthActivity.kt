package com.hyeok.example.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.CallbackManager
import com.google.firebase.auth.*
import com.hyeok.example.R
import com.hyeok.example.auth.presenter.AuthContract
import com.hyeok.example.auth.presenter.AuthPresenter
import com.hyeok.example.base.presenter.BasePresenter
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity(), AuthContract.View {
    override lateinit var presenter : AuthContract.Presenter
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mAuthListener : FirebaseAuth.AuthStateListener
    private lateinit var mCallbackManager : CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener {
            it : FirebaseAuth ->

            
        }
    }

    override fun startMain(intent : Intent) {
        startActivity(intent)
    }
}
