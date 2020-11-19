package com.deluxe.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.deluxe.todolist.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_register.*

class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        var database: DatabaseReference = Firebase.database.reference
        auth = Firebase.auth
        firebaseAnalytics = Firebase.analytics

        loginRegisterBtn.setOnClickListener {
            val emailInput: String = loginEmailET.text.toString()
            val passwordInput: String = loginEmailET.text.toString()
            if (emailInput.trim().length == 0 || passwordInput.trim().length == 0) {
                Toast.makeText(applicationContext, "Please enter an email and password ", Toast.LENGTH_SHORT)
                    .show()
                finish()
            } else {
                auth.createUserWithEmailAndPassword(
                    loginEmailET.text.toString(),
                    loginPasswordET.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("mindebug", "createUserWithEmail:success")
                            database.child("todoapp").child("users").push()
                                .setValue(auth.currentUser!!.uid)
                            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, null)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("mindebug", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                        // ...
                    }
            }
        }

        loginLoginBtn.setOnClickListener {

            val emailInput: String = loginEmailET.text.toString()
            val passwordInput: String = loginEmailET.text.toString()
            if (emailInput.trim().length == 0 || passwordInput.trim().length == 0) {
                Toast.makeText(applicationContext, "Please enter an email and password ", Toast.LENGTH_SHORT)
                    .show()
                finish()
            } else {
                auth.signInWithEmailAndPassword(
                    loginEmailET.text.toString(),
                    loginPasswordET.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("mindeug", "signInWithEmail:success")
                            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, null)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("mindebug", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
            }
        }
    }

}