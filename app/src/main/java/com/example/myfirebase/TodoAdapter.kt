    package com.example.myfirebase

    import android.graphics.Color
    import android.text.format.DateFormat
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.recyclerview.widget.RecyclerView
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.auth.ktx.auth
    import com.google.firebase.database.*
    import com.google.firebase.database.ktx.database
    import com.google.firebase.database.ktx.getValue
    import com.google.firebase.ktx.Firebase
    import kotlinx.android.synthetic.main.todo_item.view.*
    import java.text.SimpleDateFormat
    import java.util.*

    class TodoAdapter() : RecyclerView.Adapter<TodoViewHolder>() {

        var database: DatabaseReference = Firebase.database.reference
        var auth: FirebaseAuth = Firebase.auth

        var todolist = mutableListOf<Todothing>()
        var totalPoints = ""

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
            val vh = TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false))
            return vh
        }

        override fun getItemCount(): Int {
            return todolist.size
        }

        override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
            holder.todoText.text = todolist[position].taskTitle
            holder.todoPoints.text = todolist[position].taskPoints
            holder.todoCategory.text = todolist[position].taskCategory
            holder.todoDoneByTime.text = todolist[position].taskDoneTime

            if(todolist[position].done == true ){
                holder.todoDone.setBackgroundColor(Color.GREEN)
            } else {
                holder.todoDone.setBackgroundColor(Color.RED)
            }

            holder.itemView.setOnClickListener {

                if(todolist[position].done == true ){
                    database.child("todousers").child(auth.currentUser!!.uid).child(todolist[position].fbkey!!).child("done").setValue(false)
                } else {
                    database.child("todousers").child(auth.currentUser!!.uid).child(todolist[position].fbkey!!).child("done").setValue(true)
                    if(todolist[position].taskRepeatInterval!!.toInt() > 0){
                        val date = todolist[position].taskDoneTime

                        val repeatInDays = todolist[position].taskRepeatInterval!!.toLong() * 86400000
                        val newDateToSave = addDays(date, repeatInDays)
                        database.child("todousers").child(auth.currentUser!!.uid).child(todolist[position].fbkey!!).child("taskDoneTime").setValue(newDateToSave)
                    }
                }
                loadTodo()
            }

        }

        fun addDays(dateAsString: String, repeatInDays: Long): String? {
            val myDate = dateAsString
            val sdf = SimpleDateFormat("yyMMdd")
            val date: Date = sdf.parse(myDate)
            val millis: Long = date.getTime()
            val timeTotal = millis + repeatInDays
            val convertedDate = convertDate(timeTotal.toString(),"yyMMdd")
            return convertedDate
        }

        fun convertDate(dateInMilliseconds: String, dateFormat: String?): String? {
            return DateFormat.format(dateFormat, dateInMilliseconds.toLong()).toString()
        }

        fun loadTodo(){
            todolist.clear()

            val todoListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var tempTodoList = mutableListOf<Todothing>()
                    for (todochild : DataSnapshot in dataSnapshot.children){
                        val todo : Todothing? = todochild.getValue<Todothing>()
                        todo!!.fbkey = todochild.key
                        tempTodoList.add(todo!!)
                    }
                    todolist = tempTodoList
                    notifyDataSetChanged()

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("JOHANDEBUG", "loadPost:onCancelled", databaseError.toException())
                }
            }
            database.child("todousers").child(auth.currentUser!!.uid).orderByChild("taskDoneTime").addListenerForSingleValueEvent(todoListener)
        }

        fun loadTotalPoints(){

            var totalPointsRef = database.child("totalPoints").child(auth.currentUser!!.uid).orderByChild("totalPoints")
            val pointsListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var tempPoints = ""

                    for (pointschild : DataSnapshot in dataSnapshot.children){
                        tempPoints = pointschild.value.toString()
                    }
                    totalPoints = tempPoints
                    Log.w("johandebug", totalPoints)
                    notifyDataSetChanged()
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("JOHANDEBUG", "loadPost:onCancelled", databaseError.toException())
                }
            }
            totalPointsRef.addListenerForSingleValueEvent(pointsListener)

            }

    }

    class TodoViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        var todoText = view.todoTV
        var todoPoints = view.pointsTV
        var todoDone = view.todoDoneCL
        var todoCategory = view.categoryTV
        var todoDoneByTime = view.doneByTimeTV

    }

