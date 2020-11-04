package com.example.myfirebase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    addTaskBtn.setOnClickListener {

        var validateEmptyTextinput = false

        if(taskTitleTextInput.text.trim().isEmpty()) {
            validateEmptyTextinput = true
            Log.w("johandebug", "tasktitle $validateEmptyTextinput")
            Toast.makeText(activity, "Please enter a title", Toast.LENGTH_SHORT).show()
        }
        if(taskCategoryTextInput.text.trim().isEmpty()) {
            validateEmptyTextinput = true
            Log.w("johandebug", "taskcategory $validateEmptyTextinput")
            Toast.makeText(activity, "Please enter a category", Toast.LENGTH_SHORT).show()
        }
        if(taskDoneTimeTextInput.text.trim().isEmpty()) {
            validateEmptyTextinput = true
            Log.w("johandebug", "taskdonetime $validateEmptyTextinput")
            Toast.makeText(activity, "Please enter a done by time", Toast.LENGTH_SHORT).show()
        }
        if(taskDoneTimeTextInput.text.trim().isEmpty()) {
            validateEmptyTextinput = true
            Log.w("johandebug", "taskdonetime $validateEmptyTextinput")
            Toast.makeText(activity, "Please enter a done by time", Toast.LENGTH_SHORT).show()
        }
        if(taskDoneTimeTextInput.text.toString().length !== 6) {
            validateEmptyTextinput = true
            Log.w("johandebug", "taskdonetime $validateEmptyTextinput")
            Toast.makeText(activity, "Enter date in YYMMDD", Toast.LENGTH_SHORT).show()
        }

        if(validateEmptyTextinput){
            mainActivity.finish()
        } else {
            var thingtodo = Todothing(
                taskTitle = taskTitleTextInput.text.toString(),
                taskCategory = taskCategoryTextInput.text.toString(),
                taskDoneTime = taskDoneTimeTextInput.text.toString(),
                taskRepeat = taskRepeatCheckBox.isChecked(),
                taskRepeatInterval = taskRepeatIntervalTextInput.text.toString(),
                taskPoints = taskPointsTextInput.text.toString(),
                done = false
            )
            database.child("todousers").child(auth.currentUser!!.uid).push().setValue(thingtodo)

            taskTitleTextInput.setText("")
            taskCategoryTextInput.setText("")
            taskDoneTimeTextInput.setText("")
            taskPointsTextInput.setText("")
            taskRepeatIntervalTextInput.setText("")
            if (taskRepeatCheckBox.isChecked()) {
                taskRepeatCheckBox.setChecked(false);
            }
            todoAdapter.loadTodo()
        }
    }

    doneBtn.setOnClickListener {
        (activity as MainActivity).goListFragment()
    }
}


}