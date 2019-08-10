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
            userSurname.text = user.lastName
            userName.text = user.firstName
            Picasso.get().load(user.photo).error(R.drawable.ic_error_user).into(userPhoto)
            if (((!user.can_access_closed) or (user.deactivated != ""))) {
                errorUser.visibility = View.VISIBLE
            }
        }
    }
}

/*
abstract class MainAdapter:  RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    var users: MutableList<VKUser> = arrayListOf()
    var onItemClick: ((VKUser) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){

        val userSurname: TextView
        val userName: TextView
        val userPhoto: ImageView
        val errorUser: ImageButton

        init {
            userSurname = v.findViewById(com.incaze.friendscheckervk.R.id.user_surname)
            userName = v.findViewById(com.incaze.friendscheckervk.R.id.user_name)
            userPhoto = v.findViewById(com.incaze.friendscheckervk.R.id.user_photo)
            errorUser = v.findViewById(com.incaze.friendscheckervk.R.id.error_user)

            userPhoto.setOnClickListener{
                onItemClick?.invoke(users[adapterPosition])
            }
        }

        fun bind(user: VKUser) {
            userSurname.text = user.lastName
            userName.text = user.firstName
            Picasso.get().load(user.photo).error(com.incaze.friendscheckervk.R.drawable.ic_error_user).into(userPhoto)
            if (((!user.can_access_closed) or (user.deactivated != ""))) {
                errorUser.visibility = View.VISIBLE
            }
        }
    }

}
*/
