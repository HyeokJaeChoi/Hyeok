package com.hyeok.example

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hyeok.example.auth.AuthActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser : FirebaseUser? = FirebaseAuth.getInstance()?.currentUser
        currentUser?.let {
            val mainIntent : Intent = Intent(this, AuthActivity::class.java)
            startActivity(mainIntent)
        }.let {

        }
    }
}
