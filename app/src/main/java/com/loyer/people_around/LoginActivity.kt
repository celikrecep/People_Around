package com.loyer.people_around

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var TAG = "Register"
    private var mEmail: EditText? = null
    private var mPassword: EditText? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mEmail = findViewById(R.id.edtLoginEmail)
        mPassword = findViewById(R.id.edtLoginPassword)

        mAuth = FirebaseAuth.getInstance()

    }

     fun register(view: View){
        val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
         startActivity(intent)
         finish()
    }

    fun logIn(view: View){
        attemptLogin()
    }



    private fun attemptLogin(){
        var email: String = mEmail?.text!!.toString()
        var password: String = mPassword?.text!!.toString()

        var cancel = false
        var focusView: View? = null

        if(TextUtils.isEmpty(email)){
            mEmail?.error = getString(R.string.error_field_required)
            focusView = mEmail
            cancel = true
        }
        if(TextUtils.isEmpty(password)){
            mPassword?.error = getString(R.string.error_field_required)
            focusView = mPassword
            cancel = true

        }

        if(cancel){
            focusView?.requestFocus()

        }else {

            Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show()
            mAuth?.signInWithEmailAndPassword(email, password)!!.addOnCompleteListener(this) { task ->
                if (!task.isSuccessful) {
                    Log.d(TAG, "Same problem" + task.exception)
                    showErrorDialog("Hatalı Email adresi yada şifre ")
                } else {
                    Toast.makeText(this, "Login başarılı", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity,MapsActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }
    }
    private fun showErrorDialog(message: String){
     AlertDialog.Builder(this)
             .setTitle("Oops!")
             .setMessage(message)
             .setPositiveButton(android.R.string.ok,null)
             .setIcon(android.R.drawable.ic_dialog_alert)
             .show()
    }



}
