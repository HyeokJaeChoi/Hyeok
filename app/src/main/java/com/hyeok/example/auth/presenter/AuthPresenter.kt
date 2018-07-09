package com.hyeok.example.auth.presenter

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.hyeok.example.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class AuthPresenter : AuthContract.Presenter {
    val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit override var authView : AuthContract.View
    lateinit var mFacebookCallbackManager : CallbackManager
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    val facebookAuthObservable : Observable<String> = Observable.empty()

    override fun start() {

    }

    fun facebookAuthTask(mFacebookCallbackManager: CallbackManager) : String{
        lateinit var loginToken : String
        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                loginToken = result?.accessToken!!.token
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {

            }
        })
        return loginToken
    }

    override fun executeAuthTask(v : View) {
        when (v.id) {
             R.id.fb_login_button -> {
                compositeDisposable.add(
                        facebookAuthObservable
                                .subscribeOn(Schedulers.io())
                                .concatMap({it-> })
                )
            }
            R.id.rxtest -> {
                val rxtestObservable : Observable<String> = Observable.just("test", "rx", "button")
                rxtestObservable
                        .subscribeOn(Schedulers.computation())
                        .doOnNext({Log.d("currentThread1", Thread.currentThread().name + it)})
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({Log.d("currentThread2", Thread.currentThread().name + it)})
            }
        }
    }

    override fun setCredential(authToken : String) {
        lateinit var credential: AuthCredential
        credential = FacebookAuthProvider.getCredential(authToken)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(authView as AppCompatActivity) { it: Task<AuthResult> ->
                    Log.d("currentThread2", Thread.currentThread().name)
                    Log.d("FirebaseAuth Credential", "FirebaseAuth Credential completed!")
                }
    }

    override fun disposeAll() {
        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}