package com.loyer.people_around

import android.content.Intent
import android.content.SharedPreferences
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
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private val TAG = "Register"
    private var eMail: EditText? = null
    private var mName: EditText? = null
    private var mPassword: EditText? = null
    private var mConfirmPassword: EditText? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mFireBaseUser: FirebaseUser? = null
    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        eMail = findViewById(R.id.edtRegisterEmail)
        mName = findViewById(R.id.edtName)
        mPassword = findViewById(R.id.edtRegisterPassword)
        mConfirmPassword = findViewById(R.id.edtRegisterConfirm)
        mAuth = FirebaseAuth.getInstance()
        mFireBaseUser = mAuth?.currentUser
        mDatabaseReference = FirebaseDatabase.getInstance().reference
    }


    fun signIn(view: View) {

        attemptRegistration()


    }


    private fun attemptRegistration() {

        eMail?.error = null
        eMail?.error = null

        var email: String = eMail?.text!!.toString()
        var password: String = mPassword?.text!!.toString()
        var cancel = false
        var focusView: View? = null



        if (TextUtils.isEmpty(email)) {
            eMail?.error = getString(R.string.error_field_required)
            focusView = eMail
            cancel = true
        }

        if (!TextUtils.isEmpty(password) && !isValidPassword(password)) {
            mPassword?.error = getString(R.string.error_invalid_password)
            focusView = mPassword
            cancel = true

        } else if (!isEmailValid(email)) {
            eMail?.error = getString(R.string.error_invalid_email)
            focusView = eMail
            cancel = true
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {
            createFireBaseUser()

        }

    }
    //email check
    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }
    //password check
    private fun isValidPassword(password: String): Boolean {
        val confirmPassword = mConfirmPassword?.text!!.toString()
        return confirmPassword.equals(password) && password.length > 6
    }


    private fun createFireBaseUser() {

        var email: String = eMail?.text!!.toString()
        var password: String = mPassword?.text!!.toString()


        mAuth?.createUserWithEmailAndPassword(email, password)!!.addOnCompleteListener(this) { task ->
            Log.d(TAG, "createUser onComplete : " + task.isSuccessful())

            if (!task.isSuccessful) {
                Log.d(TAG, "user creation failed")
                showErrorDialog("Registration attempt failed")
            } else {
                Toast.makeText(this, "Kayıt başarılı", Toast.LENGTH_SHORT).show()
                var intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        signInAndSignOut()

    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
                .setTitle("Oops!")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
    }
        //we gonna set the user name to later get from MapsActivity
    private fun signInAndSignOut(){
        var email: String = eMail?.text!!.toString()
        var password: String = mPassword?.text!!.toString()
        var name: String = mName?.text!!.toString()


        mAuth?.signInWithEmailAndPassword(email,password)!!.addOnCompleteListener {
           var id: String?  = mFireBaseUser?.uid
            user = User(id,name,0.0,0.0)
            mDatabaseReference?.child("persons")!!.child(id).setValue(user)
            mAuth?.signOut()

        }



    }


}
