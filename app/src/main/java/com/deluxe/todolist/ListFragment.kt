package com.deluxe.todolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.todo_item.*


class ListFragment : Fragment() {

    var todoAdapter = TodoAdapter()
    var pointsAdapter = PointsAdapter()
    var tempPoints = "0"
    private lateinit var auth: FirebaseAuth
    // var database: DatabaseReference = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        taskRecView.layoutManager = LinearLayoutManager(context)
        taskRecView.adapter = todoAdapter
        pointRecView.layoutManager = LinearLayoutManager(context)
        pointRecView.adapter = pointsAdapter
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
            pointsAdapter.loadTotalPoints()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //loadTotalPoints(0)

    }

   /* fun loadTotalPoints(addpoints: Int){

        var totalPointsRef = todoAdapter.database.child("totalPoints").child(todoAdapter.auth.currentUser!!.uid).orderByChild("totalPoints")
        val pointsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                for (pointschild : DataSnapshot in dataSnapshot.children){
                    tempPoints = pointschild.value.toString()
                }
                //notifyDataSetChanged()
                Log.w("johandebug", addpoints.toString())
                totalPointsTV.text = tempPoints

            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("JOHANDEBUG", "loadPost:onCancelled", databaseError.toException())
            }
        }
        totalPointsRef.addListenerForSingleValueEvent(pointsListener)

    }

    fun getPoints(points: String){

        loadTotalPoints(points.toInt())
    }


    */
}