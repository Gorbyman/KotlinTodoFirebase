package com.example.myfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_register.*

class LoginRegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        auth = Firebase.auth

        loginRegisterBtn.setOnClickListener {

            auth.createUserWithEmailAndPassword(loginEmailET.text.toString(), loginPasswordET.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("mindebug", "createUserWithEmail:success")

                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("mindebug", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }

                    // ...
                }

        }

        loginLoginBtn.setOnClickListener {
            auth.signInWithEmailAndPassword(loginEmailET.text.toString(), loginPasswordET.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("mindeug", "signInWithEmail:success")
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("mindebug", "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }

                    // ...
                }
        }

    }
}