package com.hyeok.example.auth

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.google.firebase.auth.*
import com.hyeok.example.R
import com.hyeok.example.auth.presenter.AuthContract
import com.hyeok.example.auth.presenter.AuthPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity(), AuthContract.View {
    override lateinit var presenter : AuthContract.Presenter
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mAuthListener : FirebaseAuth.AuthStateListener
    private lateinit var mCallbackManager : CallbackManager
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        mCallbackManager = CallbackManager.Factory.create()
        mAuth = FirebaseAuth.getInstance()

        presenter = AuthPresenter().apply {
            authView = this@AuthActivity
            mFacebookCallbackManager = this@AuthActivity.mCallbackManager
        }
        mAuthListener = FirebaseAuth.AuthStateListener {
            it : FirebaseAuth ->

            //val intent = Intent(this@AuthActivity, MainActivity::class.kt)
            val currentUser : FirebaseUser? = it.currentUser
            Log.d("currentUser", currentUser.toString())
            currentUser?.run{
                Toast.makeText(applicationContext, "currentUser : " + "providerID : " + currentUser.providerId + "UID : " + currentUser.uid + "Name : " + currentUser.displayName, Toast.LENGTH_LONG).show()
                //startMain(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.disposeAll()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    fun executeLogin(v : View){
        presenter.executeAuthTask(v)
    }

    fun logout_facebook(v : View){
        LoginManager.getInstance().logOut()
        mAuth.signOut()
    }

    override fun startMain(intent : Intent) {
        finish()
        startActivity(intent)
    }
}
