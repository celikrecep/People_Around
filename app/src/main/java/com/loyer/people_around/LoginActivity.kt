package com.loyer.people_around

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.View

class LoginActivity : AppCompatActivity() {

    private var manager: FragmentManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        manager = supportFragmentManager
    }

    fun register(view: View){
        var fragmentRegister = FragmentRegister()

        var transaction: FragmentTransaction =manager!!.beginTransaction()
        transaction.add(R.id.loginActivity,fragmentRegister,"sa")
        transaction.commit()
        Log.d("REGİSTER","Button Pressed")

    }
}
