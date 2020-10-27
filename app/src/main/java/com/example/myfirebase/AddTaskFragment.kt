package com.example.myfirebase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_task.*

class AddTaskFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }


override fun onStart() {
    super.onStart()
    addTaskBtn.setOnClickListener {
        var thingtodo = Todothing(taskTitle = taskTitleTextInput.text.toString(), taskCategory = taskCategoryTextInput.text.toString(), taskDoneTime = taskDoneTimeTextInput.text.toString(), taskRepeat = taskRepeatCheckBox.isChecked(), taskRepeatInterval = taskRepeatIntervalTextInput.text.toString(), taskPoints = taskPointsTextInput.text.toString(), done = false)
        //todoadapter.database.child("todousers").child(auth.currentUser!!.uid).push().setValue(thingtodo)
        taskTitleTextInput.setText("")
        // todoadapter.loadTodo()
        Log.i("johandebug", thingtodo.toString())
    }
}


}