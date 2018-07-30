package com.hyeok.example.gif.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.hyeok.example.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var fmTransaction: FragmentTransaction

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_upload-> {
                //replaceFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_user_info -> {
                //replaceFragment()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fmTransaction = supportFragmentManager.beginTransaction()
        fmTransaction.add(R.id.fragment_container, HomeFragment()).commit()
    }

    fun replaceFragment(fragment : Fragment){
        fmTransaction.replace(R.id.fragment_container, fragment).commit()
    }
}
