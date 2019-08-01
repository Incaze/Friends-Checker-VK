package com.incaze.friendscheckervk

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdapterMainActivity : AdapterData() {

    fun addUser(users: VKUser?) {
        this.users.add(users!!)
        notifyItemChanged(getItemCount() - 1)
    }

    fun addListOfUsers(users: List<VKUser>){
        this.users.removeAll(this.users)
        this.users.addAll<VKUser>(users)
    }

    fun deleteUser(pos: Int) {
        users.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun isEmpty() : Boolean{
        return users.size == 0
    }

    fun removeAll(){
        users.removeAll(users)
        notifyDataSetChanged()
    }

    fun returnListOfUsers() : MutableList<VKUser>{
        return users
    }

    fun setup(activity: Activity, adapter: AdapterData){
        val recyclerView = activity.findViewById<RecyclerView>(R.id.activity_main_rv_users)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        val swipeHandler = object : SwipeToDeleteUser(activity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteUser(viewHolder.adapterPosition)
                if (isEmpty()) {
                    val textEmpty = activity.findViewById<TextView>(R.id.text_empty_list)
                    textEmpty.text = activity.getString(R.string.string_empty_list_add)
                    textEmpty.visibility = View.VISIBLE
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}