package com.deluxe.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.deluxe.todolist.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*

@IgnoreExtraProperties
data class Todothing(
    var fbkey: String? = null,
    var taskTitle: String = "",
    var taskCategory: String = "",
    var taskDoneTime: String = "",
    var taskRepeatInterval: String? = "",
    var taskPoints: String? = "",
    var done: Boolean = false
)

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    var todoadapter = TodoAdapter()
    var pointsadapter = PointsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoadapter.database = Firebase.database.reference
        todoadapter.auth = Firebase.auth
        auth = Firebase.auth
        firebaseAnalytics = Firebase.analytics

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)

        supportFragmentManager.beginTransaction().add(
            R.id.mainFragmentHolder,
            ListFragment()
        ).commit()

        logoutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginRegisterActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "Main")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
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
            pointsadapter.loadTotalPoints()
        }
    }

    fun goListFragment(){
        supportFragmentManager.beginTransaction().replace(
            R.id.mainFragmentHolder,
            ListFragment()
        ).commit()
    }


}