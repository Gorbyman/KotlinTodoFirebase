package com.deluxe.todolist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.deluxe.todolist.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    var todoAdapter = TodoAdapter()
    private lateinit var auth: FirebaseAuth
    // var database: DatabaseReference = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        taskRecView.layoutManager = LinearLayoutManager(context)
        taskRecView.adapter = todoAdapter
    }

    override fun onStart() {
        super.onStart()
        toAddTaskBtn.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().replace(
                R.id.mainFragmentHolder,
                AddTaskFragment()
            ).commit()
        }
        if(auth.currentUser != null){
            todoAdapter.loadTodo()
        }
    }

    fun getPoints(totalPoints: String){
        if(totalPointsTV != null) {
            totalPointsTV.text = totalPoints
        }
    }



}