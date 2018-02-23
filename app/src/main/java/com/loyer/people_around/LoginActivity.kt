package com.loyer.people_around

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var TAG = "Register"
    private var isBegin: Boolean = false
    private var manager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        manager = supportFragmentManager
    }

     fun register(view: View){
        val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
         startActivity(intent)
         finish()
    }




}
