package com.incaze.friendscheckervk.feed.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.incaze.friendscheckervk.R
import com.incaze.friendscheckervk.model.VKUser
import com.squareup.picasso.Picasso


abstract class MainAdapter: Adapter<MainAdapter.ViewHolder>() {

    override fun onCreateHolder(parent: ViewGroup): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.user_item, parent, false)

        return ViewHolder(v)
    }

    override fun onBindHolder(viewHolder: ViewHolder, data: VKUser, position: Int) {
        viewHolder.bind(users[position])
    }

    inner class ViewHolder(v: View) : Adapter.ViewHolder(v, onItemClick, users) {

        override fun bind(user: VKUser) {
            userSurname.text = user.last_name
            userName.text = user.first_name
            Picasso.get().load(user.photo).error(R.drawable.ic_error_user).into(userPhoto)
            if (((!user.can_access_closed) or (user.deactivated != ""))) {
                errorUser.visibility = View.VISIBLE
            }
        }
    }
}