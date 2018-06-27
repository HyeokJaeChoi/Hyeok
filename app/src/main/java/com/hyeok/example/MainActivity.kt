package com.hyeok.example

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.*
import com.facebook.login.widget.LoginButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.auth.*

class MainActivity : AppCompatActivity() {
    private lateinit var mCallbackManager : CallbackManager
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mAuthListener : FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mAuthListener = FirebaseAuth.AuthStateListener {
            val user : FirebaseUser? = it.currentUser
            Log.d("FirebaseLoginActivity", "onAuthStateChanged : signed_in!" + user?.uid)
            Toast.makeText(applicationContext, "providerID : " + user?.providerId + "UID : " + user?.uid + "name : " + user?.displayName, Toast.LENGTH_LONG).show()
        }
        login_facebook(fb_login_button)
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        mAuth?.removeAuthStateListener(mAuthListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    fun login_facebook(fb_Button : LoginButton?) : Unit{
        mCallbackManager = CallbackManager.Factory.create()
        fb_login_button.setReadPermissions("email", "public_profile")
        fb_login_button.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult) {
                Log.d("FacebookLoginActivity", "facebook : onSuccess" + result)
                handleFacebookAccessToken(result.accessToken)

            }

            override fun onCancel() {
                Log.d("FacebookLoginActivity", "facebook : onCancel")
            }

            override fun onError(error: FacebookException?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    fun logout_facebook(v : View){
        try {
            mAuth.signOut()
            LoginManager.getInstance().logOut()
        }catch(e : Exception){
            Log.d("LogoutError" , e.printStackTrace().toString())
        }
    }

    fun handleFacebookAccessToken(token : AccessToken) : Unit{
        Log.d("FacebookLoginActivity", "handleFacebookAccessToken" + token)
        val credential : AuthCredential = FacebookAuthProvider.getCredential(token.getToken())

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, object : OnCompleteListener<AuthResult>{
                    override fun onComplete(task: Task<AuthResult>) {
                        if(task.isSuccessful){
                            Log.d("FacebookLoginActivity", "signInWithCredential : success")
                            val user : FirebaseUser? = mAuth.currentUser
                            Toast.makeText(applicationContext, "providerID : " + user?.providerId + "UID : " + user?.uid + "name : " + user?.displayName, Toast.LENGTH_LONG).show()
                        }
                        else{
                            Log.d("FacebookLoginActivity", "signInWithCredential : failed")
                            Toast.makeText(applicationContext, "Authentication Failed", Toast.LENGTH_LONG).show()
                        }
                    }
                })
    }
}
