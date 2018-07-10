package com.hyeok.example.auth.presenter

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.hyeok.example.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class AuthPresenter : AuthContract.Presenter {
    val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit override var authView : AuthContract.View
    lateinit var mFacebookCallbackManager : CallbackManager
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun start() {

    }

    fun facebookAuthTask(mFacebookCallbackManager: CallbackManager){
        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                Log.d("accesstoken", result?.accessToken?.token)
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {

            }
        })
    }

    override fun executeAuthTask(v : View) {
        when (v.id) {
             R.id.fb_login_button -> {
                 val facebookAuthTaskObservable : Disposable = Observable
                         .just(facebookAuthTask(mFacebookCallbackManager))
                         .doOnComplete { setCredential(AccessToken.getCurrentAccessToken().token) }
                         .subscribeOn(Schedulers.io())
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribe(
                                 { Log.d("onNext", "onNext") },
                                 { e -> Log.e("onError", e?.message) },
                                 { Log.d("onComplete", "onComplete") }
                         )
                 compositeDisposable.add(facebookAuthTaskObservable)

            }
            R.id.rxtest -> {
                val rxtestObservable : Observable<String> = Observable.just("test", "rx", "button")
                rxtestObservable
                        .subscribeOn(Schedulers.computation())
                        .flatMap({it -> Observable.just(it + " " + Thread.currentThread().name)})
                        .concatMap({it -> Observable.just(it + " " + Thread.currentThread().name)})
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({Log.d("currentThread2", Thread.currentThread().name + it)})
            }
        }
    }

    override fun setCredential(authToken : String?) {
        lateinit var credential: AuthCredential
        credential = FacebookAuthProvider.getCredential(authToken!!)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(authView as AppCompatActivity) { it: Task<AuthResult> ->
                    if(it.isSuccessful)
                        Log.d("FirebaseAuth Credential", "FirebaseAuth Credential completed!" + " "  + Thread.currentThread().name)
                }
    }

    override fun disposeAll() {
        if(!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}