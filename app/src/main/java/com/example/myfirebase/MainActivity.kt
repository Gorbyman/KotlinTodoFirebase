package com.example.myfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_task.*

@IgnoreExtraProperties
data class Todothing(
    var fbkey: String? = null,
    var taskTitle: String = "",
    var taskCategory: String = "",
    var taskDoneTime: String = "",
    //var taskRepeat: Boolean? = false,
    var taskRepeatInterval: String? = "",
    var taskPoints: String? = "",
    var done: Boolean = false
)

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    var todoadapter = TodoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoadapter.database = Firebase.database.reference
        todoadapter.auth = Firebase.auth
        auth = Firebase.auth

        supportFragmentManager.beginTransaction().add(R.id.mainFragmentHolder, ListFragment()).commit()

        logoutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginRegisterActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, LoginRegisterActivity::class.java)
        if(auth.currentUser == null){
            val intent = Intent(this, LoginRegisterActivity::class.java)
              startActivity(intent)
        } else {
            todoadapter.loadTodo()
        }
    }

    fun goListFragment(){
        supportFragmentManager.beginTransaction().replace(R.id.mainFragmentHolder, ListFragment()).commit()
    }

}