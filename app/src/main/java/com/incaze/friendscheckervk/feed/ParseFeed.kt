package com.incaze.friendscheckervk.feed

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.incaze.friendscheckervk.feed.adapter.ParseAdapter
import com.incaze.friendscheckervk.R
import com.incaze.friendscheckervk.feed.adapter.Adapter
import com.incaze.friendscheckervk.model.VKUser
import com.incaze.friendscheckervk.database.DBHelper

class ParseFeed : ParseAdapter() {

    fun retainListOfUsers(users: List<VKUser>){
        val tmp: MutableList<VKUser> = arrayListOf()
        tmp.addAll<VKUser>(this.users)
        this.users.removeAll(this.users)
        for (i in 0 until tmp.size) {
            for (j in 0 until users.size) {
                if (tmp[i].id == users[j].id) {
                    this.users.add(tmp[i])
                    break
                }
            }
            continue
        }
    }

    fun notifyListOfUsers(){
        notifyDataSetChanged()
    }

    override fun setup(activity: Activity, adapter: Adapter<ViewHolder>, dbHelper: DBHelper?) {
        val recyclerView = activity.findViewById<RecyclerView>(R.id.activity_parse_rv_users)
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        adapter.onItemClick = { user ->
            val domain = user.domain
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(activity.getString(R.string.url_vk_com, domain)))
            activity.startActivity(browserIntent)
        }
    }
}