    package com.example.myfirebase

    import android.graphics.Color
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.recyclerview.widget.RecyclerView
    import com.google.firebase.auth.FirebaseAuth
    import com.google.firebase.database.DataSnapshot
    import com.google.firebase.database.DatabaseError
    import com.google.firebase.database.DatabaseReference
    import com.google.firebase.database.ValueEventListener
    import com.google.firebase.database.ktx.getValue
    import kotlinx.android.synthetic.main.todo_item.view.*

    class TodoAdapter() : RecyclerView.Adapter<TodoViewHolder>() {

        lateinit var database : DatabaseReference
        lateinit var auth: FirebaseAuth

        var todolist = mutableListOf<Todothing>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
            val vh = TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false))
            return vh
        }

        override fun getItemCount(): Int {
            return todolist.size
        }

        override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
            holder.todoText.text = todolist[position].taskTitle

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
                }
                loadTodo()
            }

        }

        fun loadTodo(){

            todolist.clear()


            val todoListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Get Post object and use the values to update the UI

                    for (todochild : DataSnapshot in dataSnapshot.children){
                        val todo : Todothing? = todochild.getValue<Todothing>()
                        todo!!.fbkey = todochild.key
                        Log.i("mindebug" , todo!!.taskTitle!!)
                        todolist.add(todo!!)
                    }

                    notifyDataSetChanged()

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("JOHANDEBUG", "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            }

            database.child("todousers").child(auth.currentUser!!.uid).orderByChild("done").addListenerForSingleValueEvent(todoListener)

        }

    }

    class TodoViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        var todoText = view.todoTV
        var todoDone = view.todoDoneCL

    }

