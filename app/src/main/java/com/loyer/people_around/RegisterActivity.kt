package com.loyer.people_around

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private val TAG = "Register"
    private var eMail: EditText? = null
    private var name:EditText? = null
    private var mPassword: EditText? = null
    private var mConfirmPassword: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        eMail = findViewById(R.id.edtRegisterEmail)
        name = findViewById(R.id.edtName)
        mPassword = findViewById(R.id.edtRegisterPassword)
        mConfirmPassword = findViewById(R.id.edtRegisterConfirm)
        mAuth = FirebaseAuth.getInstance()
    }


    fun signIn(view: View){

        attemptRegistration()

    }


    private fun attemptRegistration(){

        eMail?.error = null
        eMail?.error = null

        var email: String = eMail?.text!!.toString()
        var password: String = mPassword?.text!!.toString()
        var cancel = false
        var focusView: View? = null



        if(TextUtils.isEmpty(email)){
            eMail?.error = getString(R.string.error_invalid_email)
            focusView = eMail
            cancel = true
        }

        if(!TextUtils.isEmpty(password) && !isValidPassword(password)){
            mPassword?.error = getString(R.string.error_invalid_password)
            focusView = mPassword
            cancel = true

        }else if(!isEmailValid(email)){
            eMail?.error = getString(R.string.error_invalid_email)
            focusView = eMail
            cancel = true
        }

        if(cancel){
            focusView?.requestFocus()
        }else {
            createFireBaseUser()
        }

    }

    private fun isEmailValid(email: String):Boolean{
        return email.contains("@")
    }

    private fun isValidPassword(password: String):Boolean{
        var confirmPassword = mConfirmPassword?.text!!.toString()
        return confirmPassword.equals(password) && password.length > 6
    }



   private fun createFireBaseUser(){

        var email: String = eMail?.text!!.toString()
        var passowrd: String = mPassword?.text!!.toString()

        mAuth?.createUserWithEmailAndPassword(email,passowrd)!!.addOnCompleteListener(this){
            task ->
            Log.d("Firebase","createUser onComplete : " + task.isSuccessful())

            if(!task.isSuccessful){
                Log.d("Firebase","user creation failed")
                showErrorDialog("Registration attempt failed")
            }else{
                var intent = Intent(this@RegisterActivity,LoginActivity::class.java)
                startActivity(intent)
                finish()
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
