package com.hyeok.example.auth

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.hyeok.example.R
import com.hyeok.example.auth.presenter.AuthContract
import com.hyeok.example.auth.presenter.AuthPresenter
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity(), AuthContract.View {
    override lateinit var presenter : AuthContract.Presenter
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mAuthListener : FirebaseAuth.AuthStateListener
    private lateinit var mCallbackManager : CallbackManager
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        mAuth = FirebaseAuth.getInstance()
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
                .let{
                    mGoogleSignInClient = GoogleSignIn.getClient(this, it)
                }

        presenter = AuthPresenter().apply {
            authView = this@AuthActivity
            authViewActivity = authView as AppCompatActivity
            mAuth = this@AuthActivity.mAuth
        }

        google_login_button.setOnClickListener {
            executeLogin(it)
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
        Log.d("requestCode", requestCode.toString())
        if(requestCode == 9001){
            GoogleSignIn.getSignedInAccountFromIntent(data)?.let{
                presenter.setCredential(it.getResult(ApiException::class.java))
            }
        }
        else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data)
        }
    }

    fun executeLogin(v : View){
        when(v.id){
            R.id.fb_login_button -> {
                mCallbackManager = CallbackManager.Factory.create()
                (presenter as AuthPresenter).mCallbackManager = mCallbackManager
            }
            R.id.google_login_button -> {
                (presenter as AuthPresenter).mGoogleSignInClient = mGoogleSignInClient
            }
        }
        presenter.executeAuthTask(v)
    }

    fun logoutAll(v : View){
        LoginManager.getInstance().logOut()
        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            if(it.isSuccessful)
                Log.d("GoogleLogout", "Google Logout Success!!")
        }
        FirebaseAuth.getInstance().signOut()
    }

    override fun startMain(intent : Intent) {
        finish()
        startActivity(intent)
    }
}
