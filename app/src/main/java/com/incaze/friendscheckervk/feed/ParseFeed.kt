package com.incaze.friendscheckervk.feed

import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.incaze.friendscheckervk.feed.adapter.ParseAdapter
import com.incaze.friendscheckervk.R
import com.incaze.friendscheckervk.model.VKUser

class ParseFeed : ParseAdapter() {

    fun retainListOfUsers(users: List<VKUser>){
        val tmp: MutableList<VKUser> = arrayListOf()
        tmp.addAll<VKUser>(this.users)
        this.users.removeAll(this.users)
        for (i in 0 until tmp.size) {
            for (j in 0 until users.size) {
                if (tmp[i].id == users[j].id) this.users.add(tmp[i])
            }
        }
    }

    fun notifyListOfUsers(){
        notifyDataSetChanged()
    }

    fun addListOfUsers(users: List<VKUser>){
        this.users.removeAll(this.users)
        this.users.addAll<VKUser>(users)
    }

    fun setup(activity: Activity, adapter: ParseAdapter){
        val recyclerView = activity.findViewById<RecyclerView>(R.id.activity_parse_rv_users)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}