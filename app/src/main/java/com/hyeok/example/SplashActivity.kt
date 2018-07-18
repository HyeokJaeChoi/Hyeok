package com.hyeok.example

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hyeok.example.auth.AuthActivity
import java.util.*

class SplashActivity : AppCompatActivity() {

    private val RC_SIGN_IN : Int = 123
    lateinit var mainIntent : Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser : FirebaseUser? = FirebaseAuth.getInstance()?.currentUser
        mainIntent = Intent(this, AuthActivity::class.java)
        currentUser?.let {
            startActivity(mainIntent)
            finish()
        }.let {
            val providers : List<AuthUI.IdpConfig> = Arrays.asList(
                    AuthUI.IdpConfig.FacebookBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build(),
                    AuthUI.IdpConfig.TwitterBuilder().build()
            )
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setTheme(R.style.Theme_AppCompat_Light_NoActionBar)
                            .setLogo(R.drawable.ic_launcher_background)
                            .build(),
                    RC_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN) run {
            val response: IdpResponse = IdpResponse.fromResultIntent(data)!!

            if(resultCode == RESULT_OK) {
                mainIntent.putExtra("userInfo", response)
                startActivity(mainIntent)
                finish()
            }else{
                Toast.makeText(this, response.error?.errorCode.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }
}
