package com.example.myfirebase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    var todoAdapter = TodoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Log.w("johandebug", todoAdapter.todolist.toString())
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
        todoAdapter.loadTodo()
    }

    override fun onStart() {
        super.onStart()
        toAddTaskBtn.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction().replace(R.id.mainFragmentHolder, AddTaskFragment()).commit()
        }
    }

}