package com.hyeok.example.auth.presenter

import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.facebook.AccessToken
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*

class AuthPresenter : AuthContract.Presenter {
    val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    lateinit override var authView : AuthContract.View
    override fun start() {

    }

    override fun setCredential(buttonId : String) {
        lateinit var credential: AuthCredential
        when(buttonId){
            "fb_login_button" -> {
                val newFacebookAccessToken : AccessToken? = AccessToken.getCurrentAccessToken()
                credential = FacebookAuthProvider.getCredential(newFacebookAccessToken!!.token)
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(authView as AppCompatActivity) { it: Task<AuthResult> ->
                            Log.d("FirebaseAuth Credential", "FirebaseAuth Credential completed!") }
            }
        }
    }
}