package com.loyer.people_around

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*

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
        transaction.add(R.id.loginActivity,fragmentRegister,"fragRegister")
        transaction.commit()
        header.visibility = View.GONE
        container.visibility = View.GONE
        Log.d("REGISTER","Button Pressed")

    }

    fun signIn(view: View){

        var fragmentRegister : FragmentRegister = manager!!.findFragmentByTag("fragRegister") as FragmentRegister
        var transaction: FragmentTransaction = manager!!.beginTransaction()
        transaction.remove(fragmentRegister)
        transaction.commit()
        header.visibility = View.VISIBLE
        container.visibility = View.VISIBLE
    }
}
