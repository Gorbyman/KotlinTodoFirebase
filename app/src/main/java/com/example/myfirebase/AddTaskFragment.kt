package com.example.myfirebase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login_register.*
import kotlinx.android.synthetic.main.fragment_add_task.*

class AddTaskFragment : Fragment() {

    lateinit var database : DatabaseReference
    lateinit var auth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    var todoAdapter = TodoAdapter()
    val mainActivity = MainActivity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

override fun onStart() {
    super.onStart()

    var database: DatabaseReference = Firebase.database.reference
    auth = Firebase.auth
    firebaseAnalytics = Firebase.analytics

    addTaskBtn.setOnClickListener {

        firebaseAnalytics.logEvent("add_todo", null)

        var validateEmptyTextinput = false
        val inputText = taskDoneTimeTextInput.text
        val (digits, notDigits) = inputText.partition { it.isDigit() }

        if(taskDoneTimeTextInput.text.toString().length !== 6 || notDigits.isNotEmpty()) {
            validateEmptyTextinput = true
            Toast.makeText(activity, "Enter date in YYMMDD", Toast.LENGTH_SHORT).show()
            taskDoneTimeTextInput.text.clear()
        }
        if(taskDoneTimeTextInput.text.trim().isEmpty()) {
            validateEmptyTextinput = true
            Toast.makeText(activity, "Please enter a done by time", Toast.LENGTH_SHORT).show()
        }
        if(taskDoneTimeTextInput.text.trim().isEmpty()) {
            validateEmptyTextinput = true
            Toast.makeText(activity, "Please enter a done by time", Toast.LENGTH_SHORT).show()
        }
        if(taskCategoryTextInput.text.trim().isEmpty()) {
            validateEmptyTextinput = true
            Toast.makeText(activity, "Please enter a category", Toast.LENGTH_SHORT).show()
        }
        if(taskTitleTextInput.text.trim().isEmpty()) {
            validateEmptyTextinput = true
            Toast.makeText(activity, "Please enter a title", Toast.LENGTH_SHORT).show()
        }

        if(validateEmptyTextinput){
            mainActivity.finish()
        } else {
            var taskPoints: String?
            if(taskPointsTextInput.text.toString().isNotEmpty()){
                taskPoints = taskPointsTextInput.text.toString()
            } else {
                taskPoints = "0"
            }
            var thingtodo = Todothing(
                taskTitle = taskTitleTextInput.text.toString(),
                taskCategory = taskCategoryTextInput.text.toString(),
                taskDoneTime = taskDoneTimeTextInput.text.toString(),
                taskRepeatInterval = taskRepeatIntervalTextInput.text.toString(),
                taskPoints = taskPoints,
                done = false
            )
            database.child("todousers").child(auth.currentUser!!.uid).push().setValue(thingtodo)

            taskTitleTextInput.setText("")
            taskCategoryTextInput.setText("")
            taskDoneTimeTextInput.setText("")
            taskPointsTextInput.setText("")
            taskRepeatIntervalTextInput.setText("")
            todoAdapter.loadTodo()

            (activity as MainActivity).goListFragment()
        }
    }

    doneBtn.setOnClickListener{
        (activity as MainActivity).goListFragment()
    }
}


}