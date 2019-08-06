package com.incaze.friendscheckervk.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.incaze.friendscheckervk.R
import com.incaze.friendscheckervk.model.VKUser
import com.squareup.picasso.Picasso

open class ParseAdapter : RecyclerView.Adapter<ParseAdapter.ViewHolder>() {
    var users: MutableList<VKUser> = arrayListOf()

    class ViewHolder(v: View) : MainAdapter.ViewHolder(v) {
        override fun bind(user: VKUser) {
            userSurname.text = user.lastName
            userName.text = user.firstName
            Picasso.get().load(user.photo).error(R.drawable.ic_error_user).into(userPhoto)
            errorUser.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_item, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size
}