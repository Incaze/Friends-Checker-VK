package com.incaze.friendscheckervk.feed

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.incaze.friendscheckervk.feed.adapter.MainAdapter
import com.incaze.friendscheckervk.R
import com.incaze.friendscheckervk.SwipeToDeleteUser
import com.incaze.friendscheckervk.model.VKUser

class MainFeed : MainAdapter() {

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

    fun setup(activity: Activity, adapter: MainAdapter){
        val recyclerView = activity.findViewById<RecyclerView>(R.id.activity_main_rv_users)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        // SWIPE TO DELETE
        val swipeHandler = object : SwipeToDeleteUser(activity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteUser(viewHolder.adapterPosition)
                if (isEmpty()) {
                    val textEmpty = activity.findViewById<TextView>(R.id.text_empty_list)
                    textEmpty.text = activity.getString(R.string.string_empty_list_add)
                    textEmpty.visibility = View.VISIBLE
                    val floatBut = activity.findViewById<FloatingActionButton>(R.id.activity_main_add_users)
                    if (floatBut.visibility != View.VISIBLE) {
                        floatBut.show()
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
        //HIDE FLOAT BUTTON
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            val floatBut = activity.findViewById<FloatingActionButton>(R.id.activity_main_add_users)
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && floatBut.visibility == View.VISIBLE) {
                    floatBut.hide()
                } else if ((dy < 0 && floatBut.visibility != View.VISIBLE) or (adapter.users.isEmpty())) {
                    floatBut.show()
                }
            }
        })

    }
}